package tms.c42onl.socialnetworkproject.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionCount;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionType;
import tms.c42onl.socialnetworkproject.service.ReactionService;

import java.io.IOException;
// Сервлет для обработки HTTP-запросов, связанных с реакциями на посты.
@WebServlet("/reaction")
public class ReactionServlet extends HttpServlet {

    // Сервис для уплавления реакциями
    private ReactionService reactionService;

    @Override
    //Создаёт единственные экземпляры хранилища и сервиса
    public void init() throws ServletException {
        super.init();
        this.reactionService = (ReactionService) getServletContext().getAttribute("reactionService");
        if (this.reactionService == null) {
            throw new ServletException("ReactionService не инициализирован");
        }
    }

    @Override
    // Обрабатывает POST-запрос для добавления, изменения или удаления реакции.
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = (Long) request.getSession().getAttribute("userId");
        Long postId = Long.parseLong(request.getParameter("postId"));
        if (userId == null) {
            request.getSession().setAttribute("error", "Необходимо войти в систему");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String reactionType = request.getParameter("reactionType");

        try {
            boolean isSave = reactionService.changeReaction(userId, postId, reactionType);
            if (!isSave) {
                request.getSession().setAttribute("error", "Не удалось поставить реакцию");
            }
            response.sendRedirect(request.getContextPath() + "/posts?id=" + postId);
        }catch (Exception e){
            e.printStackTrace();
            request.getSession().setAttribute("error", "Произошла ошибка на сервере");
            response.sendRedirect(request.getContextPath() + "/posts?id=" + postId);
        }
    }

    @Override
    // Обрабатывает GET-запрос для получения статистики реакций по конкретному посту.
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = (Long) request.getSession().getAttribute("userId");
        boolean isAuthorized = (userId != null);
        request.setAttribute("isAuthorized", isAuthorized);

        Long postId = Long.parseLong(request.getParameter("postId"));
        request.setAttribute("postId", postId);

        try {
            ReactionCount reactionCount = reactionService.getCounters(postId);
            long countLike = reactionCount.getCountLike();
            long countDislike = reactionCount.getCountDislike();

            ReactionType reactionType = ReactionType.NOTHING;
            if (userId != null) {
                reactionType = reactionService.getUserReaction(userId, postId);
            }

            request.setAttribute("countLike", countLike);
            request.setAttribute("countDislike", countDislike);
            request.setAttribute("reactionType", reactionType);
        }catch (Exception e){
            e.printStackTrace();
            request.getSession().setAttribute("error", "Произошла ошибка загрузки данных");
        }
        getServletContext().getRequestDispatcher("/pages/reactions.jsp").forward(request, response);
    }
}
