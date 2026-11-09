<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<div class="mt-4">
    <h4>
        Комментарии
        <span class="badge bg-secondary">
            <c:out value="${commentCount}" default="0"/>
        </span>
    </h4>

<c:choose>
    <c:when test="${empty comments}">
        <p class="text-muted mt-3">
            Пока нет комментариев.
            Будьте первым!
        </p>
    </c:when>

    <c:otherwise>
        <div class="mt-3">
            <c:forEach var="comment" items="${comments}">
                <div class="card mb-3">

<div class="card-body">

    <div class="d-flex justify-content-between mb-2">
        <strong>
            Пользователь #<c:out value="${comment.authorId}"/>
        </strong>

        <small class="text-muted">
            <c:out value="${comment.createdAt}"/>
        </small>

    </div>

    <p class="card-text">
        <c:out value="${comment.text}"/>
    </p>

</div>

                </div>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>


<c:if test="${not empty sessionScope.userId}">
    <form action="${pageContext.request.contextPath}/comments"
          method="post" class="mt-4">

        <input type="hidden"
               name="postId"
               value="${postId}">

        <div class="mb-3">
            <label for="commentText" class="form-label">
                Ваш комментарий
            </label>

            <textarea class="form-control"
                      id="commentText"
                      name="text"
                      rows="3"
                      required></textarea>
        </div>

        <button type="submit" class="btn btn-primary">
            Отправить комментарий
        </button>
    </form>
</c:if>


</div>
