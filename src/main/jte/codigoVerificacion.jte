@param String variable

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Código de Verificación</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f7f7;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 500px;
            margin: 40px auto;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            border: 1px solid #ddd;
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
            text-align: center;
        }
        .code-box {
            background: #f3f3f3;
            border: 2px dashed #d90429;
            font-size: 24px;
            font-weight: bold;
            color: #d90429;
            padding: 16px;
            border-radius: 6px;
            text-align: center;
            display: inline-block;
            margin: 20px auto;
        }
        .footer {
            color: #666;
            font-size: 14px;
            text-align: center;
            margin-top: 32px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <span style="font-size: 1.5em; font-weight: bold; letter-spacing: 2px;">Mundo Computo</span>
        </div>
        <h2 class="title">Verificación de Seguridad</h2>
        <div class="content">
            <p>Tu código de verificación es:</p>
            <div class="code-box">${variable}</div>
            <p>Este código es válido por unos minutos.
            Si no solicitaste esta verificación, ignora este mensaje.</p>
        </div>
        <div class="footer">
            Gracias por confiar en nosotros.<br>
            <b>Equipo Mundo Computo</b>
        </div>
    </div>
</body>
</html>
