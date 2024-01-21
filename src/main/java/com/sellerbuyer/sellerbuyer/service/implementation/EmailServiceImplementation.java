package com.sellerbuyer.sellerbuyer.service.implementation;

import com.sellerbuyer.sellerbuyer.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImplementation implements EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImplementation(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vishesh.gupta@unthinkable.co");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
