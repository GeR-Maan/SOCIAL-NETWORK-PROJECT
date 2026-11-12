package tms.c42onl.socialnetworkproject.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tms.c42onl.socialnetworkproject.config.ConnectionProvider;
import tms.c42onl.socialnetworkproject.exceptions.PostNotFoundException;
import tms.c42onl.socialnetworkproject.models.PostPage;
import tms.c42onl.socialnetworkproject.models.SessionCurrentUserProvider;
import tms.c42onl.socialnetworkproject.service.ReactionService;
import tms.c42onl.socialnetworkproject.services.CommentService;
import tms.c42onl.socialnetworkproject.services.DefaultPostDetailsService;
import tms.c42onl.socialnetworkproject.services.PhotoService;
import tms.c42onl.socialnetworkproject.services.PostDetailsService;
import tms.c42onl.socialnetworkproject.services.StubPhotoService;
import tms.c42onl.socialnetworkproject.storages.JdbcCommentStorage;
import tms.c42onl.socialnetworkproject.storages.JdbcPostDetailsStorage;
import tms.c42onl.socialnetworkproject.storages.PostDetailsStorage;
import tms.c42onl.socialnetworkproject.storages.ReactionStorage;

import java.io.IOException;

@WebServlet("/posts")
public class PostDetailsServlet extends HttpServlet {

    private PostDetailsService postDetailsService;

    @Override
    public void init() throws ServletException {
        ConnectionProvider provider =
                (ConnectionProvider) getServletContext().getAttribute("provider");
        if (provider == null) {
            throw new ServletException("ConnectionProvider не инициализирован (TMS-32)");
        }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        long postId = parsePostId(req);
        if (postId <= 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный идентификатор поста");
            return;
        }

        Long currentUserId = readCurrentUserId(req);

        try {
            PostPage page = postDetailsService.getPostDetails(postId, currentUserId);

            req.setAttribute("page", page);
            req.setAttribute("postId", postId);
            req.setAttribute("comments", page.getComments());
            req.setAttribute("commentCount", page.getComments().size());
            req.setAttribute("isAuthorized", currentUserId != null);
            req.setAttribute("countLike", page.getReactions().getCountLike());
            req.setAttribute("countDislike", page.getReactions().getCountDislike());
            req.setAttribute("reactionType", page.getCurrentUserReaction());
            req.getRequestDispatcher("/WEB-INF/views/posts/details.jsp").forward(req, resp);

        } catch (PostNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            req.setAttribute("missingPostId", postId);
            req.getRequestDispatcher("/WEB-INF/views/posts/not-found.jsp").forward(req, resp);
        }
    }

    private Long readCurrentUserId(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return null;
        }
        Object raw = session.getAttribute(SessionCurrentUserProvider.SESSION_USER_KEY);
        if (raw instanceof Long v) {
            return v;
        }
        if (raw instanceof Integer v) {
            return v.longValue();
        }
        return null;
    }

    private long parsePostId(HttpServletRequest req) {
        String raw = req.getParameter("id");
        if (raw == null || raw.isBlank()) {
            return -1;
        }
        try {
            return Long.parseLong(raw.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
}

