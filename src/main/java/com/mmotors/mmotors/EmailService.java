package com.mmotors.mmotors;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Async
    public void envoyerConfirmationDossier(String destinataire, String typeOffre) {

        System.out.println("=>=>=> ENTREE DANS EmailService");

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(destinataire);
            message.setSubject("M-Motors - Confirmation de votre dossier");
            message.setText("Bonjour,\n\nVotre dossier de type " + typeOffre +
                    " a bien été reçu et est en cours de traitement." +
                    "\n\nNous reviendrons vers vous dans les plus brefs délais." +
                    "\n\nL'équipe M-Motors");
            mailSender.send(message);
            System.out.println("=>=>=> EMAIL ENVOYE A : " + destinataire);
        } catch (Exception e) {
            System.out.println("=>=>=> ERREUR ENVOI EMAIL : " + e.getMessage());
        }
    }
}

