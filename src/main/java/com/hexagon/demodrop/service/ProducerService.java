package com.hexagon.demodrop.service;

import com.hexagon.demodrop.model.Message;
import com.hexagon.demodrop.model.Token;
import com.hexagon.demodrop.model.User;
import com.hexagon.demodrop.repository.MessageRepository;
import com.hexagon.demodrop.repository.TokenRepository;
import com.hexagon.demodrop.repository.UserRepository;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProducerService {
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private EmailService emailService;
    private MessageRepository messageRepository;

    public ProducerService(UserRepository userRepository, TokenRepository tokenRepository, EmailService emailService, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.messageRepository = messageRepository;
    }

    public boolean createUser(String email, String password){
        User user = userRepository.findByEmail(email);
        if (user != null) return false;
        user = new User(email,passwordEncoder.encode(password),false);
        userRepository.save(user);

        Token token = (new Token(user.getId()));
        tokenRepository.save(token);

        //TODO add token link to text http://localhost:8080/confirm?token=53533-5355=
        emailService.sendMail("Welcome to Hexagon","Welcome!",user.getEmail());

        return true;
    }
    @Transactional
    public boolean checkToken(String secret){
        UUID id = UUID.fromString(secret);
        tokenRepository.findAll();
        Optional<Token> optionalToken = tokenRepository.findById(id);
        if (optionalToken.isPresent()){
            Token token = optionalToken.get();
            if (token.getDate().after(new Date(new Date().getTime() + 24*60*60*1000))) return false;
            User user = userRepository.findById(token.getUser_id()).orElse(null);
            if (user == null)
                return false;
            user.setEnabled(true);
            Message message = new Message("Congratulations","You are registered",user);
            messageRepository.save(message);
            userRepository.save(user);
            tokenRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //TODO create method getProfileData
    // Parameter email of user
    // returns ProfileData

    //TODO create method saveDemo
    // Parameters email,title,description,Audiofile
    // returns false/true
}
