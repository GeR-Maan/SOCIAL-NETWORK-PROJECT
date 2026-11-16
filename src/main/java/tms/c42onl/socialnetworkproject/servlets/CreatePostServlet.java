package tms.c42onl.socialnetworkproject.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tms.c42onl.socialnetworkproject.models.PublicUser;
import tms.c42onl.socialnetworkproject.services.PhotoUploadHelper;

import java.io.IOException;

@WebServlet("/posts/create")
@MultipartConfig(maxFileSize = 10*1024*1024, maxRequestSize = 50*1024*1024)
public class CreatePostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/new-post.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        PublicUser author = (PublicUser) request.getAttribute("currentUser");

        if (author == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        //Danil add
        //====
        PhotoUploadHelper.Result photoResult;
        try {
            photoResult = PhotoUploadHelper.handle(request);
        } catch (PhotoUploadHelper.PhotoException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/new-post.jsp").forward(request, response);
            return;
        }
        //====

        if (title == null || title.isBlank() || content == null || content.isBlank()) {
            request.setAttribute("error", "Заголовок и текст поста не должны быть пустыми");
            request.getRequestDispatcher("/WEB-INF/views/new-post.jsp")
                    .forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/posts");
    }
}