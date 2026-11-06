<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
    <div class="container">

        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            Social App
        </a>

        <button class="navbar-toggler"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#mainNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="mainNavbar">
            <ul class="navbar-nav me-auto">

                <%-- Общая лента доступна всем --%>
                <li class="nav-item">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/posts">
                        Общая лента
                    </a>
                </li>

                <%-- Только для авторизованного пользователя --%>
                <c:if test="${not empty currentUser}">
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/subscriptions/feed">
                            Лента подписок
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/posts/create">
                            Новый пост
                        </a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/profile/me">
                            Мой профиль
                        </a>
                    </li>
                </c:if>
            </ul>

            <ul class="navbar-nav ms-auto">
                <c:choose>
                    <%-- Гость --%>
                    <c:when test="${empty currentUser}">
                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/login">
                                Вход
                            </a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/register">
                                Регистрация
                            </a>
                        </li>
                    </c:when>

                    <%-- Авторизованный пользователь --%>
                    <c:otherwise>
                        <li class="nav-item">
                            <span class="navbar-text me-3">
                                    ${currentUser.displayName}
                            </span>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link"
                               href="${pageContext.request.contextPath}/logout">
                                Выход
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
