package gg.jte.generated.ondemand;
@SuppressWarnings("unchecked")
public final class JtenotificationInventarioGenerated {
	public static final String JTE_NAME = "notificationInventario.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,56,56,56,56,65,65,65,0,0,0,0};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String mensaje) {
		jteOutput.writeContent("\r\n<!DOCTYPE html>\r\n<html>\r\n<head>\r\n    <meta charset=\"UTF-8\">\r\n    <title>Notificación de Inventario</title>\r\n    <style>\r\n        body {\r\n            font-family: Arial, sans-serif;\r\n            background-color: #fff;\r\n            margin: 0;\r\n            padding: 0;\r\n        }\r\n        .container {\r\n            max-width: 500px;\r\n            margin: 40px auto;\r\n            background: #fff;\r\n            border-radius: 8px;\r\n            box-shadow: 0 2px 8px rgba(0,0,0,0.08);\r\n            border: 2px solid #000;\r\n            padding: 32px 24px;\r\n        }\r\n        .header {\r\n            background: #000;\r\n            color: #fff;\r\n            padding: 16px 0;\r\n            text-align: center;\r\n            border-radius: 6px 6px 0 0;\r\n        }\r\n        .title {\r\n            color: #d90429;\r\n            margin-top: 24px;\r\n            margin-bottom: 16px;\r\n            text-align: center;\r\n        }\r\n        .content {\r\n            color: #222;\r\n            font-size: 16px;\r\n            margin-bottom: 24px;\r\n        }\r\n        .footer {\r\n            color: #d90429;\r\n            font-size: 14px;\r\n            text-align: center;\r\n            margin-top: 32px;\r\n        }\r\n    </style>\r\n</head>\r\n<body>\r\n    <div class=\"container\">\r\n        <div class=\"header\">\r\n            <span style=\"font-size: 1.5em; font-weight: bold; letter-spacing: 2px;\">Mundo Computo</span>\r\n        </div>\r\n        <h2 class=\"title\">Notificación de Inventario</h2>\r\n        <div class=\"content\">\r\n            <p>");
		jteOutput.setContext("p", null);
		jteOutput.writeUserContent(mensaje);
		jteOutput.writeContent("</p>\r\n        </div>\r\n        <div class=\"footer\">\r\n            Gracias por confiar en nosotros.<br>\r\n            <b>Equipo Mundo Computo</b>\r\n        </div>\r\n    </div>\r\n</body>\r\n</html>\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String mensaje = (String)params.get("mensaje");
		render(jteOutput, jteHtmlInterceptor, mensaje);
	}
}
