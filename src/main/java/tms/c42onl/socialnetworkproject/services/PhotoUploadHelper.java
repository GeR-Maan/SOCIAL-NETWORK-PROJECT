package tms.c42onl.socialnetworkproject.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import tms.c42onl.socialnetworkproject.models.PostImage;
import tms.c42onl.socialnetworkproject.storages.FileStorage;


import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PhotoUploadHelper {

    private static final int MAX_PHOTOS = 5;
    private static final FileStorage fileStorage = new FileStorage();

    /** Результат: список PostImage (готовы к записи в БД) + имена сохранённых файлов для отката. */
    public static class Result {
        private final List<PostImage> images;
        private final List<String> savedFiles;

        public Result(List<PostImage> images, List<String> savedFiles) {
            this.images = images;
            this.savedFiles = savedFiles;
        }
        public List<PostImage> getImages() { return images; }

        /** Удалить все сохранённые файлы (при откате транзакции). */
        public void cleanup() {
            for (String f : savedFiles) fileStorage.delete(f);
        }
    }

    public static class PhotoException extends Exception {
        public PhotoException(String msg) { super(msg); }
    }

    public static Result handle(HttpServletRequest request) throws PhotoException {
        List<Part> parts = new ArrayList<>();
        try {
            for (Part p : request.getParts()) {
                if ("images".equals(p.getName()) && p.getSize() > 0) parts.add(p);
            }
        } catch (Exception e) {
            throw new PhotoException("Не удалось прочитать загруженные файлы");
        }

        if (parts.size() > MAX_PHOTOS) {
            throw new PhotoException("Не больше " + MAX_PHOTOS + " фотографий");
        }

        for (Part p : parts) {
            if (!isImage(p)) {
                throw new PhotoException("Файл «" + p.getSubmittedFileName() + "» не является изображением");
            }
        }

        List<String> saved = new ArrayList<>();
        List<PostImage> images = new ArrayList<>();
        try {
            for (int i = 0; i < parts.size(); i++) {
                String stored = fileStorage.save(parts.get(i));
                saved.add(stored);
                PostImage img = new PostImage();
                img.setFilePath(stored);
                img.setSortOrder(i + 1);
                images.add(img);
            }
        } catch (IOException e) {
            for (String f : saved) fileStorage.delete(f);
            throw new PhotoException("Не удалось сохранить изображения");
        }

        return new Result(images, saved);
    }

    private static boolean isImage(Part part) {
        try (InputStream in = part.getInputStream()) {
            return ImageIO.read(in) != null;
        } catch (IOException e) {
            return false;
        }
    }
}