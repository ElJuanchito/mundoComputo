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

@Service
@Transactional
@RequiredArgsConstructor
@Async
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendHtmlEmail(String email, String subject, String name, TemplateEmailType templateType) throws Exception {

        StringOutput output = new StringOutput();
        templateEngine.render(String.format("%s.jte", templateType.getValue()), Map.of("nombre", name), output);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(output.toString(), true); // true = HTML

        mailSender.send(message);
    }

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
