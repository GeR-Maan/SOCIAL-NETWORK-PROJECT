<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-IN/views/common/header.jsp" />
<jsp:include page="/WEB-IN/views/common/navbar.jsp" />

<div class="container">
    <div class="row">
        <div class="col-md-8">
            <h1>Общая лента</h1>

            <c:choose>
                <c:when test="${not empty feed}">
                    <c:forEach var="post" items="${feed}">
                        <c:set var="card" value="${post}" scope="request"/>
                        <jsp:include page="/WEB-INF/views/fragments/postCard.jsp"/>
                    </c:forEach>
                </c:when>

                <c:otherwise>
                    <div class="alert alert-info">
                        Пока нет публикаций. Станьте первым, кто поделится записью!
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="/WEB-IN/views/common/footer.jsp" />
