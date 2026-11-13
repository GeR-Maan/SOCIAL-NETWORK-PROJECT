<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/pages/common/header.jsp" />
<jsp:include page="/pages/common/navbar.jsp" />
<html>
<head>
    <title>Главная страница</title>
</head>
<body>
<h1>Добро пожаловать на главную страницу!</h1>
<%-- Пример чтения ID пользователя из сессии, о котором мы говорили раньше --%>
<p>Ваш ID в сессии: ${sessionScope.userId}</p>
</body>
</html>
<jsp:include page="/pages/common/footer.jsp" />