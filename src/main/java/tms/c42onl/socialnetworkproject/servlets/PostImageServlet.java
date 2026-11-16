package tms.c42onl.socialnetworkproject.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tms.c42onl.socialnetworkproject.storages.FileStorage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/posts/*/images/*")
public class PostImageServlet extends HttpServlet {
    private final FileStorage fileStorage = new FileStorage();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        String[] parts = pathInfo.split("/");
        if (parts.length < 4) { resp.sendError(400); return; }

        long postId = Long.parseLong(parts[1]);
        String filename = parts[3];

        Path file = fileStorage.resolve(filename);
        if (!Files.exists(file)) { resp.sendError(404); return; }

        resp.setContentType(getServletContext().getMimeType(filename));
        resp.setContentLengthLong(Files.size(file));
        try (InputStream in = Files.newInputStream(file);
             OutputStream out = resp.getOutputStream()) {
            in.transferTo(out);
        }
    }
}