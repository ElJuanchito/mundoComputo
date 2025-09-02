package co.edu.uniquindio.mundoComputo.services.impl;

import co.edu.uniquindio.mundoComputo.model.TemplateEmailType;
import co.edu.uniquindio.mundoComputo.services.EmailService;

import gg.jte.TemplateEngine;
import gg.jte.output.StringOutput;

import lombok.RequiredArgsConstructor;

import jakarta.mail.internet.MimeMessage;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Map;

/**
 * Implementación del servicio de envío de correos electrónicos.
 * Utiliza plantillas JTE para generar el contenido HTML y envía correos mediante JavaMailSender.
 * Los métodos son asíncronos y permiten enviar correos HTML y notificaciones parametrizadas.
 */
@Async
@Service
@Transactional
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    /**
     * {@inheritDoc}
     * Renderiza la plantilla HTML y envía el correo electrónico al destinatario.
     */
    @Override
    public void sendHtmlEmail(String email, String subject, String variable, TemplateEmailType templateType) throws Exception {

        StringOutput output = new StringOutput();
        templateEngine.render(String.format("%s.jte", templateType.getValue()), Map.of("variable", variable), output);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(output.toString(), true); // true = HTML

        mailSender.send(message);
    }

    /**
     * {@inheritDoc}
     * Renderiza la plantilla de notificación y envía el correo electrónico al destinatario.
     */
    @Override
    public void sendNotification(String email, String subject, String message, TemplateEmailType templateType) throws Exception {
        StringOutput output = new StringOutput();
        templateEngine.render(
            String.format("%s.jte", templateType.getValue()),
            Map.of("mensaje", message),
            output
        );

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(output.toString(), true);

        mailSender.send(mimeMessage);
    }
}
