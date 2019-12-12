package com.hexagon.demodrop.controler;


import com.hexagon.demodrop.object.ProfileData;
import com.hexagon.demodrop.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping
public class ProducerControler {

    private ProducerService producerService;
    public ProducerControler(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/confirm")
    RedirectView confirmReset(String token){
        producerService.checkToken(token);
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

}
