package com.jquiguantar.backend.service;
import com.jquiguantar.backend.dto.ContactFormDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service
public class ContactService {
    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

    public void processContactForm(ContactFormDto contactForm) {
        // Aquí iría la lógica real para enviar un correo electrónico
        // utilizando JavaMailSender, o integrar con un servicio de terceros como SendGrid/Mailgun.
        // Por ahora, solo lo registraremos en los logs.

        logger.info("New Contact Form Submission:");
        logger.info("Name: {}", contactForm.getName());
        logger.info("Email: {}", contactForm.getEmail());
        logger.info("Subject: {}", contactForm.getSubject());
        logger.info("Message: {}", contactForm.getMessage());

        // Si fueras a enviar un email, sería algo como:
        // SimpleMailMessage message = new SimpleMailMessage();
        // message.setFrom(contactForm.getEmail());
        // message.setTo("tu_email@ejemplo.com"); // Reemplaza con tu email
        // message.setSubject("Contacto desde Portafolio: " + contactForm.getSubject());
        // message.setText("De: " + contactForm.getName() + "\nEmail: " + contactForm.getEmail() + "\n\n" + contactForm.getMessage());
        // mailSender.send(message);

        System.out.println("Contact form processed for: " + contactForm.getEmail());
    }
}
