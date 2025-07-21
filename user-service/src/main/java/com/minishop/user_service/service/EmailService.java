// src/main/java/com/minishop/user_service/service/EmailService.java
package com.minishop.user_service.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendRegistrationConfirmation(String to, String username) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("contact@anmoun.com");
            helper.setTo(to);
            helper.setSubject("🚀 Bienvenue chez Anmoun - Votre aventure commence ici !");

            // Corps HTML
            String htmlContent = "<div style=\"font-family: Arial, sans-serif; color: #333;\">" +
                    "<img src='cid:logoImage' alt='Anmoun Logo' style='width: 120px; margin-bottom: 20px;'/>" +
                    "<h2>Bonjour " + username + ",</h2>" +
                    "<p>Bienvenue dans la communauté <strong>Anmoun</strong> ! 🎉</p>" +
                    "<p>Votre compte a été créé avec succès. Nous sommes ravis de vous accueillir.</p>" +
                    "<p style='margin-top: 20px;'>👉 <a href='https://www.anmoun.com/login' style='color: #ff6600;'>Connectez-vous ici</a></p>" +
                    "<p style='margin-top: 30px;'>Si vous avez des questions, notre équipe est là pour vous : <a href='mailto:support@anmoun.com'>support@anmoun.com</a></p>" +
                    "<br><p>À très bientôt,<br><strong>L’équipe Anmoun 🚀</strong></p>" +
                    "</div>";

            helper.setText(htmlContent, true); // true = HTML

            // Image inline (logo)
            File logo = new File("src/main/resources/static/images/logoanmon.png"); // adapte le chemin si besoin
            if (logo.exists()) {
                helper.addInline("logoImage", logo);
            }

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // log this in real app
        }
    }}