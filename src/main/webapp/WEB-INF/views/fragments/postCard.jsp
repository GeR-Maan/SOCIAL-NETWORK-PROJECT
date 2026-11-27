<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="card shadow-sm mb-3">
    <div class="card-body">
        <div class="d-flex align-items-start mb-2">
            <c:if test="${not empty card.author.avatarUrl}">
                <img src="${card.author.avatarUrl}" alt=""
                     class="rounded-circle me-2" width="40" height="40">
            </c:if>
            <div class="flex-grow-1">
                <div class="fw-semibold"><c:out value="${card.author.displayName}"/></div>
                <small class="text-muted"><c:out value="${card.createdAt}"/></small>
            </div>
        </div>

        <p class="card-text" style="white-space: pre-wrap;"><c:out value="${card.text}"/></p>

        <c:if test="${not empty card.photos}">
            <div class="card mt-3">
                <div class="card-body d-flex flex-wrap gap-2">
                    <c:forEach var="photo" items="${card.photos}">
                        <img src="${contextPath}/post-images/${photo.filePath}"
                             alt="Фотография к публикации" class="rounded"
                             style="max-width: 200px; max-height: 200px;">
                    </c:forEach>
                </div>
            </div>
        </c:if>

        <c:set var="postId" value="${card.postId}" scope="request"/>
        <c:set var="countLike" value="${card.likeCount}" scope="request"/>
        <c:set var="countDislike" value="${card.dislikeCount}" scope="request"/>
        <c:set var="isAuthorized" value="${not empty currentUser}" scope="request"/>
        <c:set var="commentCount" value="${card.commentCount}" scope="request"/>

        <hr class="my-3"/>

        <div class="d-flex align-items-center">
            <span class="text-muted small me-auto">
                💬 <c:out value="${card.commentCount}"/> комментариев
            </span>

            <jsp:include page="/pages/blocks/_reactions.jsp"/>
        </div>

        <div class="mt-2">
            <a href="${contextPath}/posts?id=${card.postId}"
               class="btn btn-outline-primary btn-sm">
                Открыть публикацию
            </a>
        </div>
    </div>
</div>
