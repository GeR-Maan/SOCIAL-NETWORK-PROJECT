package tms.c42onl.socialnetworkproject.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tms.c42onl.socialnetworkproject.models.PostCard;
import tms.c42onl.socialnetworkproject.models.User;
import tms.c42onl.socialnetworkproject.service.PostCardService;
import tms.c42onl.socialnetworkproject.storages.JdbcPostCardStorage;

import java.io.IOException;
import java.util.List;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private PostCardService postCardService;

    public void init() throws ServletException {
        // заглушка до ConnectionProvider
        this.postCardService = new PostCardService(authorId -> List.of());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        if (username == null || username.isBlank()){
            resp.sendError(400, "Parameter 'username' is required");
            return;
        }

        // заглушка под UserStorage раскомментировать строки ниже
        // User user = userService.findByUsername(username);
        // if (user == null) { resp.sendError(404); return; }

        User user = new User();
        user.setId(1);
        user.setUsername(username);
        user.setDisplayName(username);

        List<PostCard> posts = postCardService.getAuthorPosts(user.getId());

        req.setAttribute("user", user);
        req.setAttribute("posts", posts);
        req.getRequestDispatcher("/pages/profile.jsp").forward(req, resp);
    }
}
