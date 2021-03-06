package com.hexagon.demodrop.controler;


import com.hexagon.demodrop.object.ProfileData;
import com.hexagon.demodrop.service.ProducerService;
import com.hexagon.demodrop.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class TestControler {

    private UserService userService;
    private ProducerService producerService;

    public TestControler(UserService userService, ProducerService producerService) {
        this.userService = userService;
        this.producerService = producerService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> testCreateUser(){

        String email = "";
        String password = "";
        boolean result;
        String resultString;
        result = producerService.createUser("test@test.nl","secret");
        if (result)
            return ResponseEntity.status(HttpStatus.OK).body("Het is gelukt");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Het is niet gelukt");
    }


    @GetMapping("/test1")
    public ResponseEntity<ProfileData> testProfileData(){

        ProfileData profileData = producerService.getProfileData("admin@hexagon.com");
        if (profileData != null){
            return ResponseEntity.status(HttpStatus.OK).body(profileData);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    }

    @GetMapping("/test2")
    RedirectView testRedirect() throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        String uri = "http://localhost:8080/confirm?token=12345-1235-1234&email=test@test.com";
        return new RedirectView(null);
    }

}
