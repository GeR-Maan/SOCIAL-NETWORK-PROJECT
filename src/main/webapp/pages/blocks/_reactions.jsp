<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty error}">
    <div class="alert alert-danger">
            ${error}
        <c:remove var="error" scope="session"/>
    </div>
</c:if>

<c:choose>
    <c:when  test="${isAuthorized}">
    <div class="btn-group" role="group" aria-label="Basic mixed styles example">
        <form action="/reaction" method="POST">
            <input type="hidden" name="postId" value="${param.postId}">
            <input type="hidden" name="reactionType" value="LIKE">

            <button type="submit" class="btn ${reactionType == 'LIKE' ? 'btn-success' : 'btn-outline-success'}">
                👍 Like ${countLike != 0 ? countLike : ""}
            </button>
        </form>
        <form action="/reaction" method="POST">
            <input type="hidden" name="postId" value="${param.postId}">
            <input type="hidden" name="reactionType" value="DISLIKE">

            <button type="submit" class="btn ${reactionType == 'DISLIKE' ? 'btn-danger' : 'btn-outline-danger'}">
                👎 Dislike ${countDislike != 0 ? countDislike : ""}
            </button>
        </form>
    </div>
    </c:when>

    <c:otherwise>
        <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                <button type="button" class="btn btn-outline-success" disabled>
                    👍 Like ${countLike != 0 ? countLike : ""}
                </button>

                <button type="button" class="btn btn-outline-danger" disabled>
                    👎 Dislike ${countDislike != 0 ? countDislike : ""}
                </button>
        </div>
    </c:otherwise>
</c:choose>