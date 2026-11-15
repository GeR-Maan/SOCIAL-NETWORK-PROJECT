<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Поиск пользователей</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>Поиск пользователей</h2>

    <form method="get" action="${pageContext.request.contextPath}/users/search" class="mb-4">
        <div class="input-group">
            <input type="text" name="q" class="form-control"
                   placeholder="Введите имя или логин" maxlength="100"
                   value="<c:out value='${query}'/>">
            <button type="submit" class="btn btn-primary">Найти</button>
        </div>
    </form>

    <c:if test="${not empty error}">
        <div class="alert alert-danger"><c:out value="${error}"/></div>
    </c:if>

    <c:if test="${not empty query and empty error}">
        <c:choose>
            <c:when test="${not empty users}">
                <h4>Результаты поиска</h4>
                <div class="list-group">
                    <c:forEach var="user" items="${users}">
                        <div class="list-group-item">
                            <strong><c:out value="${user.login}"/></strong>
                            <div>
                                <c:out value="${user.firstName}"/>
                                <c:out value="${user.lastName}"/>
                            </div>
                                <%-- Ссылку на профиль. --%>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-info">Пользователи не найдены.</div>
            </c:otherwise>
        </c:choose>
    </c:if>
</div>
</body>
</html>
