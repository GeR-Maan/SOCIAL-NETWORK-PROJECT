package tms.c42onl.socialnetworkproject.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tms.c42onl.socialnetworkproject.models.Comment;
import tms.c42onl.socialnetworkproject.services.CommentService;
import java.io.IOException;

import jakarta.servlet.ServletException;

@WebServlet("/comments")
public class    CommentServlet extends HttpServlet {
    private CommentService commentService;

    @Override
    public void init() throws ServletException {
        commentService = (CommentService) getServletContext()
                .getAttribute("commentService");

        if (commentService == null) {
            throw new ServletException(
                    "CommentService не инициализирован"
            );
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String postIdParam = request.getParameter("postId");
        String text = request.getParameter("text");
        Long postId;

        try {
            postId = Long.parseLong(postIdParam);

            if (postId <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Некорректный ID публикации");
                return;
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Некорректный ID публикации");
            return;
        }
        if (text == null || text.isBlank()) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Комментарий не может быть пустым"
            );
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null || !(session.getAttribute("userId") instanceof Long)) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Для добавления комментария необходимо войти в аккаунт"
            );
            return;
        }
        Long authorId = (Long) session.getAttribute("userId");
        Comment comment = new Comment(
                null,
                postId,
                text,
                authorId,
                null
        );
        commentService.addComment(comment);
        response.sendRedirect(
                request.getContextPath() + "/posts?id=" + postId
        );
    }
}
