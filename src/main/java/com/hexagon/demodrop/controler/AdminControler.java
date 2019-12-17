package com.hexagon.demodrop.controler;

import com.hexagon.demodrop.service.AdminService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AdminControler {

    private AdminService adminService;

    public AdminControler(AdminService adminService) {this.adminService = adminService;}

    @PostMapping("/createuser")
    ResponseEntity<String> register(@RequestParam("email") String email,
                                    @RequestParam("password") String password) {
        if (adminService.createEmployee(email,password))
            return ResponseEntity.status(HttpStatus.OK).body("Check your email");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User already exists");
    }

    @PostMapping("/template/{id}")
    ResponseEntity<String>  saveTemplate(
            @PathVariable("id") Long id,
            @RequestParam("status") String status,
            @RequestParam("comment") String comment,
            @AuthenticationPrincipal UserDetails user) throws NotFoundException {
       if (adminService.saveTemplate(id, comment, status))
        return ResponseEntity.ok("Success");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User doesn't exists");
    }

    @DeleteMapping("/template/{id}")
    ResponseEntity<String> deleteTemplate(
            @PathVariable("id") Long id) throws NotFoundException {
        if (adminService.deleteTemplate(id))
        return ResponseEntity.ok("Success");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User does not exists");
    }

}
