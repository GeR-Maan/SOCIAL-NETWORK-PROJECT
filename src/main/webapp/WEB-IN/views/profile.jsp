<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<jsp:include page="/WEB-IN/views/common/header.jsp" />
<jsp:include page="/WEB-IN/views/common/navbar.jsp" />

<div class="container">

    <div class="row justify-content-center">
        <div class="col-md-8">

            <!-- Информация о пользователе -->
            <div class="card mb-4">
                <div class="card-body">

                    <h1 class="h3 mb-3">
                        Профиль пользователя
                    </h1>

                    <h2 class="h4">
                        <c:out value="${profile.displayName}" />
                    </h2>

                </div>
            </div>

            <!-- Публикации пользователя -->
            <div class="card mb-4" id="profile-posts">
                <div class="card-header">
                    <h3 class="h5 mb-0">Публикации</h3>
                </div>

                <div class="card-body">
                    <p class="text-muted mb-0">
                        Здесь будут отображаться публикации пользователя.
                    </p>
                </div>
            </div>

            <!-- Подписчики пользователя -->
            <div class="card mb-4" id="profile-followers">
                <div class="card-header">
                    <h3 class="h5 mb-0">Подписчики</h3>
                </div>

                <div class="card-body">
                    <p class="text-muted mb-0">
                        Здесь будет отображаться информация о подписчиках.
                    </p>
                </div>
            </div>

        </div>
    </div>

</div>

<jsp:include page="/WEB-IN/views/common/footer.jsp" />