
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/pages/common/header.jsp" />
<jsp:include page="/pages/common/navbar.jsp" />


<div class="container d-flex justify-content-center align-items-center" style="min-height: 80vh;">
    <div class="card shadow-sm" style="width: 24rem;">
        <div class="card-body p-4">
            <h4 class="card-title text-center mb-4">Вход</h4>

            <c:if test="${not empty error}">
                <div class="alert alert-danger py-2">${error}</div>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/login">
                <div class="mb-3">
                    <label for="login" class="form-label">Логин</label>
                    <input type="text" class="form-control" id="login" name="login"
                           value="${loginValue}" required autofocus>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Пароль</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Войти</button>
            </form>

            <hr class="my-3">
            <p class="text-center mb-0 small">
                Нет аккаунта?
                <!--   TMS-25 (/reg) -->
                <a href="${pageContext.request.contextPath}/reg">Зарегистрироваться</a>
            </p>
        </div>
    </div>
</div>

<jsp:include page="/pages/common/footer.jsp" />