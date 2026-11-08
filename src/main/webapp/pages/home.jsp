
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/pages/common/header.jsp" />
<jsp:include page="/pages/common/navbar.jsp" />

<div class="container">
    <div class="row">
        <div class="col-md-8">
            <h1>Общая лента</h1>

            <c:choose>
                <c:when test="${not empty posts}">
                    <c:forEach var="post" items="${posts}">
                        <div class="card mb-3">
                            <div class="card-body">
                                <h5 class="card-title">${post.title}</h5>
                                <p class="card-text">${post.content}</p>

                                <a class="btn btn-outline-primary"
                                   href="${pageContext.request.contextPath}/posts?id=${post.id}">
                                    Открыть пост
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>

                <c:otherwise>
                    <div class="alert alert-info">
                        Пока нет постов.
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<jsp:include page="/pages/common/footer.jsp" />