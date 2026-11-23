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
import tms.c42onl.socialnetworkproject.models.PublicUser;
import tms.c42onl.socialnetworkproject.services.PhotoUploadHelper;
import tms.c42onl.socialnetworkproject.storages.JdbcPostImageStorage;

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
        postService = new PostService(jdbcManager, new JdbcPostStorage(), new JdbcPostImageStorage());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/pages/create-post.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        PublicUser author = (PublicUser) req.getAttribute("currentUser");
        if (author == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        Long authorId = author.getId();

        String text;
        try {
            text = req.getParameter("text");
        } catch (IllegalStateException e) {
            showForm(req, resp, "Файл слишком большой: не больше 5 МБ на фото", null);
            return;
        }

        PhotoUploadHelper.Result upload;
        try {
            upload = PhotoUploadHelper.handle(req);
        } catch (PhotoUploadHelper.PhotoException e) {
            showForm(req, resp, e.getMessage(), text);
            return;
        }

        Long postId;
        try {
            postId = postService.createPost(authorId, text, upload.getImages());
        } catch (IllegalArgumentException e) {
            upload.cleanup();
            showForm(req, resp, e.getMessage(), text);
            return;
        } catch (RuntimeException e) {
            upload.cleanup();
            getServletContext().log("Не удалось сохранить пост с фотографиями", e);
            showForm(req, resp, "Не удалось сохранить пост, попробуйте ещё раз", text);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/posts?id=" + postId);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp, String error, String text) throws ServletException, IOException {
        req.setAttribute("error", error);
        req.setAttribute("text", text);
        req.getRequestDispatcher("/pages/create-post.jsp").forward(req, resp);
    }
}