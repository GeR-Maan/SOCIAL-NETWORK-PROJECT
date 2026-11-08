package tms.c42onl.socialnetworkproject.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionCount;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionType;
import tms.c42onl.socialnetworkproject.service.ReactionService;
import tms.c42onl.socialnetworkproject.storages.ReactionStorage;

import java.io.IOException;

@WebServlet("/reaction")
public class ReactionServlet extends HttpServlet {

    private ReactionService reactionService;

    @Override
    public void init() throws ServletException {
        super.init();
        ReactionStorage reactionStorage = new ReactionStorage();
        this.reactionService = new ReactionService(reactionStorage);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = 1L;    // TODO: настроить брать из сессии
        Long postId = Long.parseLong(request.getParameter("postId"));
        String reactionType = request.getParameter("reactionType");

        reactionService.changeReaction(userId, postId, reactionType);
        response.sendRedirect("/reaction?postId=" + postId);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = 1L;    // TODO: настроить брать из сессии
        Long postId = Long.parseLong(request.getParameter("postId"));

        ReactionCount reactionCount = reactionService.getCounters(postId);
        long countLike = reactionCount.getCountLike();
        long countDislike = reactionCount.getCountDislike();

        ReactionType reactionType = reactionService.getUserReaction(userId, postId);

        request.setAttribute("countLike", countLike);
        request.setAttribute("countDislike", countDislike);
        request.setAttribute("reactionType", reactionType);

        getServletContext().getRequestDispatcher("/pages/reactions.jsp").forward(request, response);
    }
}
