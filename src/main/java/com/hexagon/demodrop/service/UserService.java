package com.hexagon.demodrop.service;

import com.hexagon.demodrop.model.Message;
import com.hexagon.demodrop.model.User;
import com.hexagon.demodrop.model.Token;
import com.hexagon.demodrop.object.LoginData;
import com.hexagon.demodrop.repository.MessageRepository;
import com.hexagon.demodrop.repository.TokenRepository;
import com.hexagon.demodrop.repository.UserRepository;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private EmailService emailService;
    private MessageRepository messageRepository;
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    //@Autowired
    public UserService(UserRepository userRepository, TokenRepository tokenRepository, EmailService emailService, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.messageRepository = messageRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByEmail(username);
        if (user == null) throw new UsernameNotFoundException(String.format("User %s not found", username));
        return user;
    }

    public LoginData getLoginData(String email) {

        User user = userRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException(String.format("Username[%s] not found", email));
        return new LoginData(user);
    }

    public boolean SaveProfile(String email,
                               String name,
                               String description,
                               MultipartFile file) throws IOException, UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException(String.format("Username[%s] not found", email));
        user.setName(name);
        user.setDescription(description);
        user.setPhoto(file.getBytes());
        user.setThumbnail(createThumbnail(file, 100).toByteArray());
        Message message = new Message("Congratulations", "You updated your profile", user);
        messageRepository.save(message);
        user.getMessages().add(message);
        userRepository.save(user);
        return true;
    }

    private ByteArrayOutputStream createThumbnail(MultipartFile originalFile, Integer width) throws IOException {
        ByteArrayOutputStream thumbOutput = new ByteArrayOutputStream();
        BufferedImage thumbImg = null;
        BufferedImage img = ImageIO.read(originalFile.getInputStream());
        thumbImg = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, width, Scalr.OP_ANTIALIAS);
        ImageIO.write(thumbImg, originalFile.getContentType().split("/")[1], thumbOutput);
        return thumbOutput;
    }

    public boolean ResetPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException(String.format("Username[%s] not found", email));

        Token token = (new Token(user.getId()));
        tokenRepository.save(token);

        emailService.sendMail(
                "Password Reset",
                "Hexagonian,\n You've requested a password reset.\n Click the link below to reset your password.\n http://localhost:8080/confirmreset?token=" + token.getId() + "\nIf you didn't request a reset you can ignore this email.",
                user.getEmail());
        return false;
    }

    public String ConfirmResetPassword(String secret) {
        UUID id = UUID.fromString(secret);
        tokenRepository.findAll();
        Optional<Token> optionalToken = tokenRepository.findById(id);
        if (optionalToken.isPresent()) {
            Token token = optionalToken.get();
            if (token.getDate().after(new Date(new Date().getTime() + 24 * 60 * 60 * 1000)))
                throw new UsernameNotFoundException("Token has expired"); // TODO: change throw
            User user = userRepository.findById(token.getUser_id()).orElse(null);
            if (user == null)
                throw new UsernameNotFoundException("User not found"); //TODO: change throw
            user.setEnabled(false);
            userRepository.save(user);
            tokenRepository.deleteById(id);

            Token newToken = new Token(user.getId());
            tokenRepository.save(newToken);
            return String.format("/change?token=%s&email=%s", newToken.getId().toString(), user.getEmail());
        }
        return "";

    }

    public boolean ChangePassword(String secret, String password) {
        UUID id = UUID.fromString(secret);
        tokenRepository.findAll();
        Optional<Token> optionalToken = tokenRepository.findById(id);
        if (optionalToken.isPresent()) {
            Token token = optionalToken.get();
            if (token.getDate().after(new Date(new Date().getTime() + 24 * 60 * 60 * 1000)))
                throw new UsernameNotFoundException("Token has expired"); // TODO: change throw
            User user = userRepository.findById(token.getUser_id()).orElse(null);
            if (user == null)
                throw new UsernameNotFoundException("User not found"); //TODO: change throw
            user.setEnabled(true);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            emailService.sendMail(
                    "Your password has been reset",
                    "Hexagonian,\n Your password has been reset",
                    user.getEmail());
            return false;
        }

        return false;
    }

}
