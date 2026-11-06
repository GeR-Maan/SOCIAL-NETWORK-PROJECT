<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Публикация не найдена</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<main class="container py-5 text-center" style="max-width: 640px;">
    <h1 class="h4 mb-3">Публикация не найдена</h1>
    <p class="text-muted">
        Публикация с идентификатором <strong>${missingPostId}</strong> не существует или была удалена.
    </p>
    <a href="${pageContext.request.contextPath}/feed" class="btn btn-primary">Вернуться к ленте</a>
</main>
</body>
</html>