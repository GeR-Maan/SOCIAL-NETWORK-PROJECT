<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
    <head>
        <title>Registration</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@6.0.0-alpha.1/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-B/GM4XqrwHnWXNOWMbloTmrYXZg10cakYGmpfsR/bbzQ6JAJI4ihuyADKLnBgrCe" crossorigin="anonymous">
    </head>
    <body>
        <div class="container">
            <div class="row justify-content-center mt-5">
                <div class="col-4">
                    <p>Регистрация</p>
                </div>
            </div>

            <div class="row justify-content-center mt-5">
                <div class="col-4">

                    <c:if test = "${not empty errorMessage}">
                        <div class = "alert theme-danger" role = "alert">
                            ${errorMessage}
                        </div>
                    </c:if>

                    <form action="/reg" method="post">
                        <div class="mb-3">
                            <label for="login" class="form-label">Логин</label>
                            <input
                            type="text"
                            class="form-control"
                            name="login"
                            id="login"
                            value = "${not empty invalidUser ? invalidUser.login : ''}" >
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">Пароль</label>
                            <input
                            type="password"
                            class="form-control"
                            id="password"
                            name="password" >
                            value = "${not empty invalidUser ? invalidUser.password : ''}"
                            </div>

                        <div class="mb-3">
                            <label for="firstName" class="form-label">Имя</label>
                            <input
                            type="text"
                            class="form-control"
                            id="firstName"
                            name="firstName"
                            value = "${not empty invalidUser ? invalidUser.firstName : ''}">
                        </div>

                        <div class="mb-3">
                            <label for="lastName" class="form-label">Фамилия</label>
                            <input
                            type="text"
                            class="form-control"
                            id="lastName"
                            name="lastName"
                            value = "${not empty invalidUser ? invalidUser.lastName : ''}">
                        </div>

                        <div class="mb-3">
                            <label for="birthDate" class="form-label">Дата рождения</label>
                            <input
                            type="date"
                            class="form-control"
                            id="birthDate"
                            name="birthDate"
                            value = "${not empty invalidUser ? invalidUser.birthDate : ''}">
                        </div>

                        <button type="submit" class="btn btn-primary w-100">Создать аккаунт</button>
                    </form>

                    <div class="mt-3 text-center">
                        <a href="#">Уже есть аккаунт? Войти</a>
                    </div>
                </div>
            </div>
        </div>

        <script type="module" src="https://cdn.jsdelivr.net/npm/bootstrap@6.0.0-alpha.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-1a/pXj49ZQ1aHEmrJ+gMw1otqoVsYwlEnlD8mIfY2TV03r20Y0CN7uqx1tQogjPL" crossorigin="anonymous"></script>
    </body>
</html>