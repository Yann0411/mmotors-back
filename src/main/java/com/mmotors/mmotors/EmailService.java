package com.mmotors.mmotors;

import com.resend.Resend;

import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final String apiKey;

    public EmailService(@Value("${resend.api.key}") String apiKey) {
        this.apiKey = apiKey;
    }

    @Async
    public void envoyerConfirmationDossier(String destinataire, String typeOffre) {
        try {
            Resend resend = new Resend(apiKey);

            CreateEmailOptions params = CreateEmailOptions.builder()
                    .from("onboarding@resend.dev")
                    .to(destinataire)
                    .subject("M-Motors - Confirmation de votre dossier")
                    .html("<p>Bonjour,</p><p>Votre dossier de type <strong>" + typeOffre +
                            "</strong> a bien été reçu et est en cours de traitement.</p>" +
                            "<p>Nous reviendrons vers vous dans les plus brefs délais.</p>" +
                            "<p>L'équipe M-Motors</p>")
                    .build();

            CreateEmailResponse response = resend.emails().send(params);
            System.out.println("=>=>=> EMAIL ENVOYE A : " + destinataire + " | id : " + response.getId());
        } catch (Exception e) {
            System.out.println("=>=>=> ERREUR ENVOI EMAIL : " + e.getMessage());
        }
    }
}
