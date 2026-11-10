package tms.c42onl.socialnetworkproject.services;

//Заглушка

import jakarta.servlet.http.Part;

import java.sql.Connection;
import java.util.List;

public class StubPostPhotoService implements PostPhotoService {
    @Override
    public void save(Connection conn, Long postId, List<Part> photos) {
        System.out.println("Заглушка: пост " + postId + ", фото: " + photos.size());
    }
}