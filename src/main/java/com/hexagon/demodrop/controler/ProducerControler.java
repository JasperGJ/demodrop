package com.hexagon.demodrop.controler;


import com.hexagon.demodrop.object.ProfileData;
import com.hexagon.demodrop.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@RestController
@RequestMapping
public class ProducerControler {

    private ProducerService producerService;
    public ProducerControler(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/confirm")
    RedirectView confirmReset(String token){
        boolean result = producerService.checkToken(token);

        return new RedirectView("/loginform");
    }

    @PostMapping("/register")
    ResponseEntity<String> loginFailure(@RequestParam("email") String email,
                                           @RequestParam("password") String password) {
        if (producerService.createUser(email,password))
            return ResponseEntity.status(HttpStatus.OK).body("check your mail");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Fail");
    }

    @GetMapping("/profile")
    ResponseEntity<ProfileData>  getProfile(@AuthenticationPrincipal UserDetails user){
        ProfileData data = producerService.getProfileData(user.getUsername());
        return ResponseEntity.ok(data);
    }

    @PostMapping("/demodrop")
    ResponseEntity<String>  saveProfile(
            @RequestParam("name") String title,
            @RequestParam("description") String description,
            @RequestParam("audio") MultipartFile file,
            @AuthenticationPrincipal UserDetails user) throws IOException {

        System.out.println(String.format("Dropped demo for %s, with %s, %s",user.getUsername(), title, description));
        boolean result = producerService.SaveDemo(
                user.getUsername(),
                title,
                description,
                file);

        return ResponseEntity.ok("Ok");
    }


}
