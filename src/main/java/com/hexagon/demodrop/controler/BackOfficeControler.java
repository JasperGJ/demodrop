package com.hexagon.demodrop.controler;

import com.hexagon.demodrop.object.DemoData;
import com.hexagon.demodrop.object.InboxData;
import com.hexagon.demodrop.service.BackOfficeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/demo/{id}")
    ResponseEntity<DemoData> getDemo(@PathVariable("id") long id){
        DemoData data = backOfficeService.getDemoData(id);
        return ResponseEntity.ok(data);
    }
}
