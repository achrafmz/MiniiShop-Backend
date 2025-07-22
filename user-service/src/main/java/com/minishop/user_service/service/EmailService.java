// src/main/java/com/minishop/user_service/service/EmailService.java
package com.minishop.user_service.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
//import org.springframework.mail.SimpleMailMessage;
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
            String htmlContent = "<div style=\"font-family: Arial, sans-serif; color: #333; max-width: 600px; margin: auto; padding: 20px;\">" +
                    "<center><img src='cid:logoImage' alt='Logo Anmoun' style='width: 120px; margin-bottom: 20px;'/></center>" +

                    "<center><h2 style=\"color: #ff6600;\">Bienvenue sur Anmoun, " + username + " !</h2></center>" +

                    "<p>Félicitations 🎉 Votre compte a été <strong>créé avec succès</strong>.</p>" +

                    "<p>Nous sommes ravis de vous compter parmi nous. Grâce à votre compte, vous pouvez désormais :</p>" +
                    "<ul style=\"line-height: 1.6;\">" +
                    "<li>📝 Publier vos propres annonces</li>" +
                    "<li>🔍 Rechercher facilement des services ou produits</li>" +
                    "<li>💬 Contacter les annonceurs en toute simplicité</li>" +
                    "</ul>" +

                    "<center><p style=\"margin-top: 20px;\"> <a href='https://www.anmoun.com/login' style='color: #ffffff; background-color: #ff6600; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Se connecter à mon compte</a></p></center>" +

                    "<p style=\"margin-top: 30px;\">Une question ou besoin d’aide ? Notre équipe est à votre écoute : <a href='mailto:support@anmoun.com'>support@anmoun.com</a></p>" +

                    "<hr style=\"margin: 40px 0;\">" +

                    "<p style=\"font-size: 14px; color: #888;\">Cet e-mail vous a été envoyé suite à la création de votre compte sur <strong>Anmoun</strong>. Si vous n’êtes pas à l’origine de cette action, veuillez nous contacter immédiatement.</p>" +

                    "<p style=\"margin-top: 30px;\">À très bientôt,<br><strong>L’équipe Anmoun 🚀</strong></p>" +
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