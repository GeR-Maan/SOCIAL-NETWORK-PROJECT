<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty sessionScope.userId and sessionScope.userId != profile.id}">
    <c:choose>
        <c:when test="${isSubscribed}">
            <form action="${pageContext.request.contextPath}/subs" method="post" class="mt-2">
                <input type="hidden" name="authorId" value="${profile.id}" />
                <input type="hidden" name="action" value="unsubscribe" />
                <button type="submit" class="btn btn-outline-danger btn-sm">Отписаться</button>
            </form>
        </c:when>
    <c:otherwise>
            <form action="${pageContext.request.contextPath}/subs" method="post" class="mt-2">
                <input type="hidden" name="authorId" value="${profile.id}" />
                <input type="hidden" name="action" value="subscribe" />
                <button type="submit" class="btn btn-primary btn-sm">Подписаться</button>
            </form>
    </c:otherwise>
    </c:choose>
</c:if>

<div class="card mb-4" id="profile-followers">
    <div class="card-header d-flex justify-content-between align-items-center">
        <h3 class="h5 mb-0">Подписчики</h3>
        <span class="badge bg-secondary">${countSubscribers}</span>
    </div>
    <div class="card-body p-0">
        <c:choose>
            <c:when test="${not empty subscribers}">
                <ul class="list-group list-group-flush">
                    <c:forEach var="sub" items="${subscribers}">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <a href="${pageContext.request.contextPath}/profile?id=${sub.id}" class="text-decoration-none">
                                <c:out value="${sub.firstName} ${sub.lastName}" />
                            </a>
                            <span class="text-muted small">@<c:out value="${sub.login}" /></span>
                        </li>
                    </c:forEach>
                </ul>
            </c:when>
        <c:otherwise>
            <p class="text-muted p-3 mb-0"></p>
        </c:otherwise>
        </c:choose>
    </div>
</div>