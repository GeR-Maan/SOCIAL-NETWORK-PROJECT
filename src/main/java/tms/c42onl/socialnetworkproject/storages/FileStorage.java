package tms.c42onl.socialnetworkproject.storages;

import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileStorage {
    private final Path baseDir = Path.of("/uploads/posts"); // или из конфига

    public String save(Part part) throws IOException {
        String originalName = part.getSubmittedFileName();
        String ext = getExtension(originalName);        // getExtension сам умеет с null

        String filename = postId + "_" + UUID.randomUUID() + ext;
        Path target = baseDir.resolve(filename);
        Files.createDirectories(baseDir);
        try (InputStream in = part.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }
        return filename;
    }

    public Path resolve(String filename) {
        return baseDir.resolve(filename);
    }

    public void delete(String filename) {
        try { Files.deleteIfExists(baseDir.resolve(filename)); }
        catch (IOException ignored) {}
    }

    private String getExtension(String filename) {
        if (filename == null) return "";
        int dot = filename.lastIndexOf('.');
        if (dot < 0 || dot == filename.length() - 1) return "";
        return filename.substring(dot);
    }
}
