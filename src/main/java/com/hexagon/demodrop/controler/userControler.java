package com.hexagon.demodrop.controler;

import com.hexagon.demodrop.object.LoginData;
import com.hexagon.demodrop.object.ProfileData;
import com.hexagon.demodrop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@RestController
@RequestMapping
public class userControler {

    private UserService userService;

    public userControler(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/loginSucces")
    ResponseEntity<LoginData> loginSucces(@RequestParam("username") String username,
                                          @RequestParam("password") String password) {
        System.out.println(String.format("/loginSucces with -> username : %s password : %s", username, password));
        LoginData loginData = userService.getLoginData(username);
        if (loginData != null) return ResponseEntity.status(HttpStatus.OK).body(loginData);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    @PostMapping("/loginFailure")
    ResponseEntity<LoginData> loginFailure(@RequestParam("username") String username,
                                          @RequestParam("password") String password) {
        System.out.println(String.format("/loginFailure with -> username : %s password : %s", username, password));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @GetMapping("/reset")
    ResponseEntity<String> reset(@RequestParam("email") String email){
        userService.ResetPassword(email);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    @GetMapping("/confirmreset")
    RedirectView confirmReset(String token){
        String uri = userService.ConfirmResetPassword(token);
        return new RedirectView(uri);
    }

    @PostMapping("/changepassword")
    ResponseEntity<String> changePassword(String token,String password){
        userService.ChangePassword(token,password);
        return ResponseEntity.status(HttpStatus.OK).body("Mail hes been sent");
    }

    @PostMapping("/profile")
    ResponseEntity<String>  saveProfile(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("photo") MultipartFile file,
            @AuthenticationPrincipal UserDetails user) throws IOException {

        System.out.println(String.format("Updated profile for %s, with %s, %s",user.getUsername(), name,description));
        boolean result = userService.SaveProfile(
                user.getUsername(),
                name,
                description,
                file);

        return ResponseEntity.ok("Ok");
    }

}
