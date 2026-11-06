<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:include page="/WEB-INF/views/common/header.jsp" />
<jsp:include page="/WEB-INF/views/common/navbar.jsp" />

<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-7">
            <h1 class="h4 mb-3">Новый пост</h1>

            <form method="post"
                  action="${pageContext.request.contextPath}/posts/create">

                <div class="mb-3">
                    <label class="form-label" for="title">Заголовок</label>
                    <input class="form-control"
                           type="text"
                           id="title"
                           name="title"
                           required>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="content">Текст поста</label>
                    <textarea class="form-control"
                              id="content"
                              name="content"
                              rows="6"
                              required></textarea>
                </div>

                <%-- ID автора НЕ передаём через форму --%>

                <button class="btn btn-primary" type="submit">
                    Опубликовать
                </button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
