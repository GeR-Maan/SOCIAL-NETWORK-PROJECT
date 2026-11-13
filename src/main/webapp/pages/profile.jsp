<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"  uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Профиль — <c:out value="${user.displayName}"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body>
<div class="container mt-4">

    <!-- Шапка профиля -->
    <div class="card mb-4">
        <div class="card-body">
            <div class="d-flex align-items-center">
                <c:choose>
                    <c:when test="${not empty user.avatarUrl}">
                        <img src="<c:out value='${user.avatarUrl}'/>" class="rounded-circle me-3"
                             alt="avatar" width="64" height="64">
                    </c:when>
                    <c:otherwise>
                        <div class="rounded-circle bg-secondary text-white me-3 d-flex align-items-center justify-content-center"
                             style="width:64px;height:64px;font-size:1.5rem">
                            <c:out value="${fn:toUpperCase(fn:substring(user.displayName, 0, 1))}"/>
                        </div>
                    </c:otherwise>
                </c:choose>
                <div>
                    <h4 class="mb-0"><c:out value="${user.displayName}"/></h4>
                    <span class="text-muted">@<c:out value="${user.username}"/></span>
                </div>
            </div>
        </div>
    </div>

    <!-- Блок публикаций -->
    <h5>Публикации</h5>

    <c:choose>
        <c:when test="${empty posts}">
            <div class="alert alert-info">У пользователя пока нет публикаций.</div>
        </c:when>
        <c:otherwise>
            <c:forEach var="post" items="${posts}">
                <div class="card mb-3">
                    <div class="card-body">
                        <!-- Шапка карточки -->
                        <div class="d-flex align-items-center mb-2">
                            <c:choose>
                                <c:when test="${not empty post.author.avatarUrl}">
                                    <img src="<c:out value='${post.author.avatarUrl}'/>" class="rounded-circle me-2"
                                         alt="avatar" width="32" height="32">
                                </c:when>
                                <c:otherwise>
                                    <div class="rounded-circle bg-secondary text-white me-2 d-flex align-items-center justify-content-center"
                                         style="width:32px;height:32px;font-size:0.8rem">
                                        <c:out value="${fn:toUpperCase(fn:substring(post.author.displayName, 0, 1))}"/>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <small class="text-muted">
                                <c:out value="${post.author.displayName}"/> ·
                                <c:out value="${post.createdAt}"/>
                            </small>
                        </div>

                        <!-- Текст поста -->
                        <p class="card-text"><c:out value="${post.preview}"/></p>

                        <!-- Фотографии -->
                        <c:if test="${not empty post.photos}">
                            <div class="row g-2 mb-2">
                                <c:forEach var="photo" items="${post.photos}">
                                    <div class="col-6 col-md-4">
                                        <img src="<c:out value='${photo.url}'/>" class="img-fluid rounded" alt="photo">
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>

                        <!-- Счётчики -->
                        <div class="d-flex gap-3 text-muted small">
                            <span>💬 <c:out value="${post.counters.comments}"/></span>
                            <span>❤️ <c:out value="${post.counters.reactions}"/></span>
                            <a href="${pageContext.request.contextPath}<c:out value='${post.permalink}'/>"
                               class="text-decoration-none ms-auto">
                                Перейти к посту
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>

</div>
</body>
</html>
