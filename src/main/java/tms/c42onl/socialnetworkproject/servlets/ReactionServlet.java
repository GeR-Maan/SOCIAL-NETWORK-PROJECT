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
// Сервлет для обработки HTTP-запросов, связанных с реакциями на посты.
@WebServlet("/reaction")
public class ReactionServlet extends HttpServlet {

    // Сервис для уплавления реакциями
    private ReactionService reactionService;

    @Override
    //Создаёт единственные экземпляры хранилища и сервиса
    public void init() throws ServletException {
        super.init();
        ReactionStorage reactionStorage = new ReactionStorage();
        this.reactionService = new ReactionService(reactionStorage);
    }

    @Override
    // Обрабатывает POST-запрос для добавления, изменения или удаления реакции.
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = 1L;    // TODO: настроить брать из сессии
        Long postId = Long.parseLong(request.getParameter("postId"));
        String reactionType = request.getParameter("reactionType");

        boolean isSave = reactionService.changeReaction(userId, postId, reactionType);
        if(!isSave){
            request.getSession().setAttribute("error", "Не удалось поставить реакцию");
        }
        response.sendRedirect("/reaction?postId=" + postId);
    }

    @Override
    // Обрабатывает GET-запрос для получения статистики реакций по конкретному посту.
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("isAuthorized", request.getSession().getAttribute("user"));
        request.setAttribute("isAuthorized", true); //TODO: убрать эту строку

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
