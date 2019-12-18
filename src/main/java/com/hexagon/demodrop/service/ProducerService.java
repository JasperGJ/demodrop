package com.hexagon.demodrop.service;

import com.hexagon.demodrop.model.*;
import com.hexagon.demodrop.object.ProfileData;
import com.hexagon.demodrop.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
public class ProducerService {
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private String hostname;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private TokenRepository tokenRepository;
    private EmailService emailService;
    private MessageRepository messageRepository;
    private DemoRepository demoRepository;

    public ProducerService(@Value("${cust.hostname}")String hostname,
                           UserRepository userRepository, RoleRepository roleRepository, TokenRepository tokenRepository, EmailService emailService, MessageRepository messageRepository, DemoRepository demoRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.messageRepository = messageRepository;
        this.demoRepository = demoRepository;
        this.hostname = hostname;
    }

    public boolean createUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) return false;
        user = new User(email, passwordEncoder.encode(password), false);
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        user.setRoles(roles);
        userRepository.save(user);

        Token token = (new Token(user.getId()));
        tokenRepository.save(token);

        emailService.sendMail(
                "Welcome to Hexagon",
                "Click on the link to confirm you registration: "+hostname+"/confirm?token=" + token.getId(),
                user.getEmail());

        return true;
    }

    @Transactional
    public boolean checkToken(String secret) {
        UUID id = UUID.fromString(secret);
        tokenRepository.findAll();
        Optional<Token> optionalToken = tokenRepository.findById(id);
        if (optionalToken.isPresent()) {
            Token token = optionalToken.get();
            if (token.getDate().after(new Date(new Date().getTime() + 24 * 60 * 60 * 1000))) return false;
            User user = userRepository.findById(token.getUser_id()).orElse(null);
            if (user == null)
                return false;
            user.setEnabled(true);
            Message message = new Message("Congratulations", "You are registered", user);
            messageRepository.save(message);
            user.getMessages().add(message);
            userRepository.save(user);
            tokenRepository.deleteById(id);
            return true;
        }
        return false;

    }

    public ProfileData getProfileData(String email) {

        User user = userRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException(String.format("Username[%s] not found", email));
        return new ProfileData(user);
    }

    public boolean SaveDemo(String email,
                            String title,
                            String description,
                            MultipartFile file) throws IOException, UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException(String.format("Username[%s] not found", email));
        Demo demo = new Demo();
        demo.setUser(user);
        demo.setTitle(title);
        demo.setDescription(description);
        demo.setAudio(file.getBytes());
        demo.setUploaded(new Date());
        demo.setStatus("new");
        demoRepository.save(demo);
        user.getDemos().add(demo);
        Message message = new Message("Congratulations", "You uploaded a demo", user);
        messageRepository.save(message);
        user.getMessages().add(message);
        userRepository.save(user);
        return true;
    }
}
