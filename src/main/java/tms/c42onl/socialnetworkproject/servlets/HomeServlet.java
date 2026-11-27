package tms.c42onl.socialnetworkproject.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tms.c42onl.socialnetworkproject.config.ConnectionProvider;
import tms.c42onl.socialnetworkproject.config.JDBCManager;
import tms.c42onl.socialnetworkproject.models.PostCard;
import tms.c42onl.socialnetworkproject.services.CommentService;
import tms.c42onl.socialnetworkproject.services.DefaultPostFeedService;
import tms.c42onl.socialnetworkproject.services.PostFeedService;
import tms.c42onl.socialnetworkproject.storages.JdbcPostFeedStorage;
import tms.c42onl.socialnetworkproject.storages.JdbcPostImageStorage;
import tms.c42onl.socialnetworkproject.storages.PostFeedStorage;
import tms.c42onl.socialnetworkproject.service.ReactionService;

import java.io.IOException;
import java.util.List;

@WebServlet({"/", "/home"})
public class HomeServlet extends HttpServlet {

    private PostFeedService feedService;

    @Override
    public void init() throws ServletException {
        ConnectionProvider provider =
                (ConnectionProvider) getServletContext().getAttribute("provider");
        if (provider == null) {
            throw new ServletException("ConnectionProvider не инициализирован (TMS-32)");
        }

        JDBCManager jdbcManager = (JDBCManager) getServletContext().getAttribute("connection");
        if (jdbcManager == null) {
            throw new ServletException("JDBCManager не инициализирован (TMS-32)");
        }

        CommentService commentService = (CommentService) getServletContext().getAttribute("commentService");
        ReactionService reactionService = (ReactionService) getServletContext().getAttribute("reactionService");

        PostFeedStorage feedStorage = new JdbcPostFeedStorage(provider);
        this.feedService = new DefaultPostFeedService(
                feedStorage,
                jdbcManager,
                new JdbcPostImageStorage(),
                reactionService,
                commentService
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<PostCard> feed = feedService.getFeed();
        request.setAttribute("feed", feed);
        request.getRequestDispatcher("/WEB-IN/views/home.jsp")
                .forward(request, response);
    }
}
