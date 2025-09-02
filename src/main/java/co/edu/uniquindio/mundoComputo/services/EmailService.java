package co.edu.uniquindio.mundoComputo.services;

import co.edu.uniquindio.mundoComputo.model.TemplateEmailType;

/**
 * Servicio para el envío de correos electrónicos en el sistema.
 * Permite enviar correos HTML y notificaciones utilizando plantillas parametrizadas.
 */
public interface EmailService {

    /**
     * Envía un correo electrónico en formato HTML utilizando una plantilla parametrizada.
     * @param email Correo electrónico de destino
     * @param subject Asunto del correo
     * @param name Variable a incluir en la plantilla
     * @param templateType Tipo de plantilla a utilizar
     * @throws Exception si ocurre un error en el envío
     */
    void sendHtmlEmail(String email, String subject, String name, TemplateEmailType templateType) throws Exception;

    /**
     * Envía una notificación por correo electrónico utilizando una plantilla parametrizada.
     * @param email Correo electrónico de destino
     * @param subject Asunto del correo
     * @param message Mensaje a incluir en la plantilla
     * @param templateType Tipo de plantilla a utilizar
     * @throws Exception si ocurre un error en el envío
     */
    void sendNotification(String email, String subject, String message, TemplateEmailType templateType) throws Exception;
}
