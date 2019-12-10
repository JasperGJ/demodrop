package com.hexagon.demodrop.service;

import com.hexagon.demodrop.model.User;
import com.hexagon.demodrop.object.LoginData;
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

    private ByteArrayOutputStream createThumbnail(MultipartFile orginalFile, Integer width) throws IOException {
        ByteArrayOutputStream thumbOutput = new ByteArrayOutputStream();
        BufferedImage thumbImg = null;
        BufferedImage img = ImageIO.read(orginalFile.getInputStream());
        thumbImg = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, width, Scalr.OP_ANTIALIAS);
        ImageIO.write(thumbImg, orginalFile.getContentType().split("/")[1] , thumbOutput);
        return thumbOutput;
    }

}
