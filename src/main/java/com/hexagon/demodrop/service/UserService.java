package com.hexagon.demodrop.service;

import com.hexagon.demodrop.model.User;
import com.hexagon.demodrop.model.Token;
import com.hexagon.demodrop.object.LoginData;
import com.hexagon.demodrop.repository.TokenRepository;
import com.hexagon.demodrop.repository.UserRepository;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByEmail(username);
        if (user == null) throw new UsernameNotFoundException(String.format("User %s not found",username));
        return user;
    }

    public LoginData getLoginData(String email){

        User user = userRepository.findByEmail(email);
        if (user == null ) throw new UsernameNotFoundException(String.format("Username[%s] not found",email));
        return new LoginData(user);
    }

    public boolean SaveProfile(String email,
                               String name,
                               String description,
                               MultipartFile file) throws IOException, UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user == null ) throw new UsernameNotFoundException(String.format("Username[%s] not found",email));
        user.setName(name);
        user.setDescription(description);
        user.setPhoto(file.getBytes());
        user.setThumbnail(createThumbnail(file,100).toByteArray());
        userRepository.save(user);
        return true;
    }

    private ByteArrayOutputStream createThumbnail(MultipartFile originalFile, Integer width) throws IOException {
        ByteArrayOutputStream thumbOutput = new ByteArrayOutputStream();
        BufferedImage thumbImg = null;
        BufferedImage img = ImageIO.read(originalFile.getInputStream());
        thumbImg = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, width, Scalr.OP_ANTIALIAS);
        ImageIO.write(thumbImg, originalFile.getContentType().split("/")[1] , thumbOutput);
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
















    //TODO method confirmreset
    // paraneter token
    // returns redirectstring  https://locolhost:8080/reset?token=1334343-12332-323-32&email=admin@admin.com

    //TODO method changepassword
    // paramter token, password
    // return true/false
}
