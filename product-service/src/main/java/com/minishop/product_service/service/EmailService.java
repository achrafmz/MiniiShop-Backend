// src/main/java/com/minishop/product_service/service/EmailService.java
package com.minishop.product_service.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAnnoncePublishedEmail(String to, String username, String annonceTitle) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("contact@anmoun.com");
            helper.setTo(to);
            helper.setSubject("✅ Votre annonce \"" + annonceTitle + "\" est en ligne !");

            // 🔽 HTML élégant et professionnel
            String htmlContent = "<!DOCTYPE html>" +
                    "<html lang='fr'>" +
                    "<head>" +
                    "    <meta charset='UTF-8'>" +
                    "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                    "    <title>Votre annonce est publiée !</title>" +
                    "    <style>" +
                    "        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; margin: 0; padding: 0; color: #333; }" +
                    "        .container { max-width: 650px; margin: 30px auto; background: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 8px 20px rgba(0,0,0,0.1); }" +
                    "        .header { background: linear-gradient(135deg, #ff6600, #ff9900); padding: 30px; text-align: center; }" +
                    "        .header img { width: 130px; margin-bottom: 15px; }" +
                    "        .header h1 { color: white; margin: 0; font-size: 24px; font-weight: 600; }" +
                    "        .content { padding: 30px; text-align: center; }" +
                    "        .content h2 { color: #ff6600; margin-top: 0; }" +
                    "        .content p { line-height: 1.7; color: #555; }" +
                    "        .btn { display: inline-block; margin: 25px 0; padding: 14px 28px; background-color: #ff6600; color: white; text-decoration: none; border-radius: 6px; font-weight: bold; font-size: 16px; }" +
                    "        .btn:hover { background-color: #e55c00; }" +
                    "        .footer { text-align: center; padding: 20px; font-size: 13px; color: #aaa; background-color: #f1f1f1; border-top: 1px solid #eee; }" +
                    "        .footer a { color: #888; text-decoration: none; }" +
                    "        .footer a:hover { color: #ff6600; }" +
                    "        .icon-list { text-align: left; margin: 20px auto; max-width: 400px; }" +
                    "        .icon-list li { margin: 10px 0; color: #555; }" +
                    "        .icon-list li::before { content: '✓'; color: #ff6600; font-weight: bold; margin-right: 8px; }" +
                    "    </style>" +
                    "</head>" +
                    "<body>" +
                    "    <div class='container'>" +
                    "        <div class='header'>" +
                    "            <img src='cid:logoImage' alt='Logo Anmoun'>" +
                    "            <h1>Votre annonce est en ligne !</h1>" +
                    "        </div>" +
                    "        <div class='content'>" +
                    "            <h2>Félicitations, " + username + " !</h2>" +
                    "            <p>🎉 <strong>\"" + annonceTitle + "\"</strong> est désormais publiée sur <strong>Anmoun</strong> et visible par des milliers d'utilisateurs.</p>" +
                    "            <ul class='icon-list'>" +
                    "                <li>Votre annonce apparaît dans les catégories pertinentes</li>" +
                    "                <li>Les acheteurs peuvent vous contacter directement</li>" +
                    "                <li>Vous pouvez suivre les vues et les messages</li>" +
                    "            </ul>" +
                    "            <p>Vous pouvez la gérer à tout moment depuis votre tableau de bord.</p>" +
                    "            <a href='https://www.anmoun.com/mes-annonces' class='btn'>Voir mes annonces</a>" +
                    "            <p>Une question ? <a href='mailto:support@anmoun.com' style='color: #ff6600;'>support@anmoun.com</a></p>" +
                    "        </div>" +
                    "        <div class='footer'>" +
                    "            <p>Cet email a été envoyé car vous avez publié une annonce sur <strong>Anmoun</strong>.<br>" +
                    "            Si vous n'êtes pas à l'origine de cette action, veuillez nous contacter.</p>" +
                    "            <p>&copy; 2025 Anmoun. Tous droits réservés.</p>" +
                    "        </div>" +
                    "    </div>" +
                    "</body>" +
                    "</html>";

            helper.setText(htmlContent, true);

            // 🔽 Ajouter le logo en inline
            File logo = new File("src/main/resources/static/images/logoanmon.png");
            if (logo.exists()) {
                helper.addInline("logoImage", logo);
            }

            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}