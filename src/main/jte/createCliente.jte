@param String nombre

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bienvenido a Mundo Computo</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fff;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 500px;
            margin: 40px auto;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            border: 2px solid #000;
            padding: 32px 24px;
        }
        .header {
            background: #000;
            color: #fff;
            padding: 16px 0;
            text-align: center;
            border-radius: 6px 6px 0 0;
        }
        .title {
            color: #d90429;
            margin-top: 24px;
            margin-bottom: 16px;
            text-align: center;
        }
        .content {
            color: #222;
            font-size: 16px;
            margin-bottom: 24px;
        }
        .footer {
            color: #d90429;
            font-size: 14px;
            text-align: center;
            margin-top: 32px;
        }
        .button {
            display: inline-block;
            color: #d90429;
            text-decoration: none;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <span style="font-size: 1.5em; font-weight: bold; letter-spacing: 2px;">Mundo Computo</span>
        </div>
        <h2 class="title">¡Bienvenido, ${nombre}!</h2>
        <div class="content">
            <p>Tu cuenta ha sido <b>creada exitosamente</b> en <span style="color:#d90429;">Mundo Computo</span>.</p>
            <p>Ya puedes acceder a nuestros servicios y disfrutar de todos los beneficios que ofrecemos.</p>
            <p>Si tienes alguna duda, puedes <a href="mailto:soporte@mundo-computo.com" class="button">contactarnos</a> en cualquier momento.</p>
        </div>
        <div class="footer">
            ¡Gracias por unirte a nosotros!<br>
            <b>Equipo Mundo Computo</b>
        </div>
    </div>
</body>
</html>