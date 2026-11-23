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

@WebServlet("/post-images/*")
public class PostImageServlet extends HttpServlet {
    private final FileStorage fileStorage = new FileStorage();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();

        // FileStorage сохраняет фотографии под именем UUID с расширением.
        if (path == null || !path.matches(
                "/[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}(?:\\.[a-zA-Z0-9]+)?")) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String filename = path.substring(1);
        Path file = fileStorage.resolve(filename);
        if (!Files.isRegularFile(file)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        try (var input = new java.io.BufferedInputStream(Files.newInputStream(file))) {
            String contentType = java.net.URLConnection.guessContentTypeFromStream(input);
            resp.setContentType(contentType != null && contentType.startsWith("image/")
                    ? contentType : "application/octet-stream");
            resp.setHeader("X-Content-Type-Options", "nosniff");
            resp.setContentLengthLong(Files.size(file));
            input.transferTo(resp.getOutputStream());
        }
    }
}