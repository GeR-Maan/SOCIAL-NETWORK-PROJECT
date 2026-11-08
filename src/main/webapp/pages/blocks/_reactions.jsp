<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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