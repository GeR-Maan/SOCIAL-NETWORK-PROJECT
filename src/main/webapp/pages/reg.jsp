<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                    <p>Registration</p>
                </div>
            </div>



            <div class="row justify-content-center mt-5">
                <div class="col-4">
                    <form action="/reg" method="post">
                        <div class="mb-3">
                            <label for="login" class="form-label">Login</label>
                            <input
                            type="text"
                            class="form-control"
                            name="login"
                            id="login"
                            value = "${not empty invalidUser ? invalidUser.login : ''}" required>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input
                            type="password"
                            class="form-control"
                            id="password"
                            name="password" required>
                            </div>

                        <div class="mb-3">
                            <label for="firstName" class="form-label">Name</label>
                            <input
                            type="text"
                            class="form-control"
                            id="firstName"
                            name="firstName"
                            value = "${not empty invalidUser ? invalidUser.firstName : ''}">
                        </div>

                        <div class="mb-3">
                            <label for="lastName" class="form-label">Last Name</label>
                            <input
                            type="text"
                            class="form-control"
                            id="lastName"
                            name="lastName"
                            value = "${not empty invalidUser ? invalidUser.lastName : ''}">
                        </div>

                        <div class="mb-3">
                            <label for="birthDate" class="form-label">Birthday</label>
                            <input
                            type="date"
                            class="form-control"
                            id="birthDate"
                            name="birthDate"
                            value = "${not empty invalidUser ? invalidUser.birthDate : ''}">
                        </div>

                        <button type="submit" class="btn btn-primary w-100">Create Account</button>
                    </form>

                    <div class="mt-3 text-center">
                        <a href="#">Already have an account? Log in</a>
                    </div>
                </div>
            </div>
        </div>

        <script type="module" src="https://cdn.jsdelivr.net/npm/bootstrap@6.0.0-alpha.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-1a/pXj49ZQ1aHEmrJ+gMw1otqoVsYwlEnlD8mIfY2TV03r20Y0CN7uqx1tQogjPL" crossorigin="anonymous"></script>
    </body>
</html>