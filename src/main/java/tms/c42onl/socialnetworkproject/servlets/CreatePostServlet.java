package tms.c42onl.socialnetworkproject.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import tms.c42onl.socialnetworkproject.config.JDBCManager;
import tms.c42onl.socialnetworkproject.services.PostService;
import tms.c42onl.socialnetworkproject.services.StubPostPhotoService;
import tms.c42onl.socialnetworkproject.storages.JdbcPostStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/posts/create")
@MultipartConfig(maxFileSize = 5*1024*1024, maxRequestSize = 30*1024*1024)
public class CreatePostServlet extends HttpServlet {

    private PostService postService;

    @Override
    public void init() {
        JDBCManager jdbcManager = (JDBCManager) getServletContext().getAttribute("connection");
        postService = new PostService(jdbcManager, new JdbcPostStorage(), new StubPostPhotoService());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/pages/create-post.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // Моя заглушка пока нет входа пользователя автор это юзер с id 1.
        Long authorId = 1L;

        String text = req.getParameter("text");

        List<Part> photos = new ArrayList<>();

        try {
            for (Part part : req.getParts()) {
                if ("photos".equals(part.getName()) && part.getSize() > 0) {
                    photos.add(part);
                }
            }
        } catch (IllegalStateException e) {
            showForm(req, resp, "Файл слишком большой: не больше 5 МБ на фото", text);
            return;
        }

        try {
            Long postId = postService.createPost(authorId, text, photos);
            resp.sendRedirect(req.getContextPath() + "/post?id=" + postId);

        } catch (IllegalArgumentException e) {

            showForm(req, resp, e.getMessage(), text);

        } catch (RuntimeException e) {
            e.printStackTrace();
            showForm(req, resp, "Не удалось сохранить пост, попробуйте ещё раз", text);
        }
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, String error, String text) throws ServletException, IOException {
        req.setAttribute("error", error);
        req.setAttribute("text", text);
        req.getRequestDispatcher("/pages/create-post.jsp").forward(req, resp);
    }
}