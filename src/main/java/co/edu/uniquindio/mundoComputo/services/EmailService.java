package co.edu.uniquindio.mundoComputo.services;

import co.edu.uniquindio.mundoComputo.model.TemplateEmailType;

public interface EmailService {

    void sendHtmlEmail(String email, String subject, String name, TemplateEmailType templateType) throws Exception;
    void sendNotification(String email, String subject, String message, TemplateEmailType templateType) throws Exception;
}
