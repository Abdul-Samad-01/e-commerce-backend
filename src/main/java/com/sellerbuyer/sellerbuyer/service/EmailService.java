package com.sellerbuyer.sellerbuyer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


public interface EmailService {

    public void sendSimpleMessage(String to, String subject,
                                  String text);
}
