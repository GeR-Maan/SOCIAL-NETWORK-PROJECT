<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Новый пост</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container py-4" style="max-width: 640px">
    <h1 class="h3 mb-4">Новый пост</h1>
    <c:if test="${not empty error}">
    <div class="alert alert-danger"><c:out value="${error}"/></div>
    </c:if>

    <form method="post"
          action="${pageContext.request.contextPath}/posts/create"
          enctype="multipart/form-data">
        <div class="mb-3">
            <label for="text" class="form-label">Текст</label>
            <textarea id="text" name="text" class="form-control" rows="5"><c:out value="${text}"/></textarea>
        </div>
        <div class="mb-3">
            <label for="photos" class="form-label">Фото</label>
            <input id="photos" name="photos" type="file" class="form-control" accept="image/*" multiple>
            <div class="form-text">Можно не больше 5 фото, каждое до 5 МБ.</div>
        </div>

        <button type="submit" class="btn btn-primary">Опубликовать</button>
    </form>
</div>
</body>
</html>
