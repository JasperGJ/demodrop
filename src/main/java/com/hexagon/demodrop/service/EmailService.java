package com.hexagon.demodrop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    public JavaMailSender emailSender;
/*  application  propperties
    spring.mail.host=mail.robbiekeeris.nl
    spring.mail.port=587
    spring.mail.username=<hexagon@robbiekeeris.nl>
    spring.mail.password=<hexagon>
    spring.mail.properties.mail.smtp.auth=false
    spring.mail.properties.mail.smtp.starttls.enable=true
*/

    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    void sendMail(String title, String text, String email){
        System.out.println(String.format("Mail to : %s\n subject: %s\n %s\n---------------",email,title,text));
        //sendSimpleMessage(email,title,text);
    }

}
