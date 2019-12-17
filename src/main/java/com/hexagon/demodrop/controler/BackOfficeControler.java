package com.hexagon.demodrop.controler;

import com.hexagon.demodrop.model.Template;
import com.hexagon.demodrop.object.DemoData;
import com.hexagon.demodrop.object.InboxData;
import com.hexagon.demodrop.service.BackOfficeService;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("/templates")
    ResponseEntity<List<Template>> getTemplates(){
        return ResponseEntity.ok(backOfficeService.getTemplates());
    }

    @GetMapping("/demo/{id}")
    ResponseEntity<DemoData> getDemo(@PathVariable("id") long id){
        DemoData data = backOfficeService.getDemoData(id);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/review/{id}")
    ResponseEntity<String> setReview(@PathVariable("id") long id,
                                       @AuthenticationPrincipal UserDetails user) throws NotFoundException {
        backOfficeService.setReview(id,user.getUsername());
        return ResponseEntity.ok("gelukt");
    }

    @PostMapping("/review/{id}")
    ResponseEntity<String>  setComment(
            @PathVariable("id") long id,
            @RequestParam("status") String status,
            @RequestParam("comment") String comment,
            @AuthenticationPrincipal UserDetails user) throws IOException, NotFoundException {
        backOfficeService.setComment(id, user.getUsername(), comment, status);
        return ResponseEntity.ok("gelukt");
    }
}
