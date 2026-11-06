package tms.c42onl.socialnetworkproject.servlets;
import tms.c42onl.socialnetworkproject.Exceptions.PostNotFoundException;
import tms.c42onl.socialnetworkproject.Services.PostDetailsService;
import tms.c42onl.socialnetworkproject.models.PostPage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "postDetailsServlet", urlPatterns = "/posts")
public class PostDetailsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static final String ATTR_SERVICE = "postDetailsService";
    public static final String ATTR_CURRENT_USER = "currentUserService";

    private PostDetailsService postDetailsService;
    private CurrentUserService currentUserService;

    public PostDetailsServlet() { }

    @Override
    public void init() throws ServletException {
        this.postDetailsService = (PostDetailsService)
                getServletContext().getAttribute(ATTR_SERVICE);
        this.currentUserService = (CurrentUserService)
                getServletContext().getAttribute(ATTR_CURRENT_USER);

        if (postDetailsService == null || currentUserService == null) {
            throw new ServletException(
                    "PostDetailsServlet: зависимости не инициализированы. " +
                            "Проверьте, что DetailsModuleListener зарегистрирован.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        long postId = parsePostId(req);
        if (postId <= 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Некорректный идентификатор поста");
            return;
        }

        Long currentUserId = currentUserService.getCurrentUserId(req);

        try {
            PostPage page = postDetailsService.getPostDetails(postId, currentUserId);
            req.setAttribute("page", page);
            req.getRequestDispatcher("/WEB-INF/views/posts/details.jsp").forward(req, resp);
        } catch (PostNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            req.setAttribute("missingPostId", postId);
            req.getRequestDispatcher("/WEB-INF/views/posts/not-found.jsp").forward(req, resp);
        }
    }

    private long parsePostId(HttpServletRequest req) {
        String raw = req.getParameter("id");
        if (raw == null || raw.isBlank()) return -1;
        try {
            return Long.parseLong(raw.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
