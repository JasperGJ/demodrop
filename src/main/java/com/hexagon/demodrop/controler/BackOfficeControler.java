package com.hexagon.demodrop.controler;

import com.hexagon.demodrop.object.InboxData;
import com.hexagon.demodrop.service.BackOfficeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class BackOfficeControler {
    private BackOfficeService backOfficeService;

    public BackOfficeControler(BackOfficeService backOfficeService) {
        this.backOfficeService = backOfficeService;
    }

    @GetMapping("/inbox")
    ResponseEntity<InboxData> getProfile(@AuthenticationPrincipal UserDetails user){
        InboxData data = backOfficeService.getInboxData(user.getUsername());
        return ResponseEntity.ok(data);
    }
}
