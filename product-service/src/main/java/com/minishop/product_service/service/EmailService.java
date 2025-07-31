// src/main/java/com/minishop/product_service/service/EmailService.java
package com.minishop.product_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAnnoncePublishedEmail(String to, String title) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ton.email@gmail.com");
        message.setTo(to);
        message.setSubject("✅ Annonce publiée avec succès !");
        message.setText("""
            Bonjour,
            
            Félicitations ! Votre annonce "%s" a été publiée avec succès sur MiniShop.
            
            Elle est maintenant visible par des milliers d'utilisateurs.
            
            Merci de faire partie de notre communauté !
            
            L'équipe MiniShop
            """.formatted(title));

        mailSender.send(message);
    }
}