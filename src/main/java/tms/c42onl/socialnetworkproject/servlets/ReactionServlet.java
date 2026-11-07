package tms.c42onl.socialnetworkproject.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        response.sendRedirect(request.getHeader("referer"));
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h2>Тест лайков</h2>");
        response.getWriter().println("<form action='/reaction' method='POST'>");
        response.getWriter().println("<input type='text' name='postId' value='1'>");
        response.getWriter().println("<input type='text' name='reactionType' value='LIKE'>");
        response.getWriter().println("<button type='submit'>Отправить лайк</button>");
        response.getWriter().println("</form></body></html>");
    }
}
