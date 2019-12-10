package com.hexagon.demodrop.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    void sendMail(String title, String text, String email){
        System.out.println(String.format("Mail to : %s/n subject: %s/n %s/n---------------",email,title,text));
    }
}
