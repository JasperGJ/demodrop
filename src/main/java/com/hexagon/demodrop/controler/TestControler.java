package com.hexagon.demodrop.controler;


import com.hexagon.demodrop.service.ProducerService;
import com.hexagon.demodrop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
