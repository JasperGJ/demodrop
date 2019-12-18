package com.hexagon.demodrop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public JavaMailSender emailSender;
    private boolean enableSMTP;

    public EmailService(@Value("${cust.enableSMTP}") boolean enableSMTP,JavaMailSender emailSender) {
        this.enableSMTP = enableSMTP;
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    void sendMail(String title, String text, String email){
        System.out.println(String.format(
                        "\n--------------------------------" +
                        "\n--------------------------------" +
                        "\nMail to : %s" +
                        "\n subject: %s" +
                        "\n--------------------------------" +
                        "\n %s" +
                        "\n--------------------------------",email,title,text));
        if (enableSMTP)
            sendSimpleMessage(email,title,text);
    }

}
