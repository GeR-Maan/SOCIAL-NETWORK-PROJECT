
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-IN/views/common/header.jsp" />
<jsp:include page="/WEB-IN/views/common/navbar.jsp" />

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card">
                <div class="card-body">
                    <h1 class="h4 mb-3">Вход</h1>

                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger">
                                ${errorMessage}
                        </div>
                    </c:if>

                    <form method="post"
                          action="${pageContext.request.contextPath}/login">

                        <div class="mb-3">
                            <label class="form-label" for="username">
                                Имя пользователя
                            </label>
                            <input class="form-control"
                                   type="text"
                                   id="username"
                                   name="username"
                                   required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label" for="password">
                                Пароль
                            </label>
                            <input class="form-control"
                                   type="password"
                                   id="password"
                                   name="password"
                                   required>
                        </div>

                        <button class="btn btn-primary w-100" type="submit">
                            Войти
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/pages/common/footer.jsp" />