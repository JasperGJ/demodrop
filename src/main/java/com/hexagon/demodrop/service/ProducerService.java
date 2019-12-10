package com.hexagon.demodrop.service;

import com.hexagon.demodrop.model.Token;
import com.hexagon.demodrop.model.User;
import com.hexagon.demodrop.repository.TokenRepository;
import com.hexagon.demodrop.repository.UserRepository;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private EmailService emailService;

    public ProducerService(UserRepository userRepository, TokenRepository tokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    public boolean createUser(String email, String password){
        User user = userRepository.findByEmail(email);
        if (user != null) return false;
        user = new User(email,passwordEncoder.encode(password),false);
        userRepository.save(user);

        Token token = (new Token(user.getId()));
        tokenRepository.save(token);

        emailService.sendMail("Welcome to Hexagon","Welcome!",user.getEmail());

        return true;
    }
}
