<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
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

    <a href="${pageContext.request.contextPath}/home" class="btn btn-link px-0 mb-2">
        &larr; К ленте
    </a>
    <div class="card shadow-sm mb-3">
        <div class="card-body">
            <div class="d-flex align-items-center mb-3">
                <c:if test="${not empty page.author.avatarUrl}">
                    <img src="${page.author.avatarUrl}" alt=""
                         class="rounded-circle me-2" width="40" height="40">
                </c:if>
                <div>
                    <div class="fw-semibold">
                        <c:out value="${page.author.displayName}"/>
                    </div>
                    <small class="text-muted">
                        <c:out value="${page.createdAt}"/>
                    </small>
                </div>
            </div>
            <p class="card-text" style="white-space: pre-wrap;"><c:out value="${page.text}"/></p>
        </div>
    </div>

    <c:if test="${not empty page.photos}">
        <div class="card shadow-sm mb-3">
            <div class="card-body d-flex flex-wrap gap-2">
                <c:forEach var="p" items="${page.photos}">
                    <img src="${p.url}" alt=""
                         class="rounded" style="max-width: 200px; max-height: 200px;">
                </c:forEach>
            </div>
        </div>
    </c:if>


    <jsp:include page="/pages/blocks/_reactions.jsp"/>
    <jsp:include page="/pages/blocks/_comments.jsp"/>

</main>
</body>
</html>
</html>