<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Публикация — <c:out value="${page.author.displayName}"/></title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<main class="container py-4" style="max-width: 760px;">

    <a href="${pageContext.request.contextPath}/feed" class="btn btn-link px-0 mb-2">&larr; К ленте</a>

    <%-- Общая карточка публикации TMS-30 --%>
    <jsp:include page="/WEB-INF/views/fragments/post-card.jsp"/>

    <%-- Фотографии (TMS-17) --%>
    <jsp:include page="/WEB-INF/views/fragments/post-photos.jsp"/>

    <%-- Реакции (TMS-23) --%>
    <jsp:include page="/WEB-INF/views/fragments/post-reactions.jsp"/>

    <%-- Комментарии (TMS-22) --%>
    <jsp:include page="/WEB-INF/views/fragments/post-comments.jsp"/>

</main>
</body>
</html>