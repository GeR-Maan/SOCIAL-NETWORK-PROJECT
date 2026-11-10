package tms.c42onl.socialnetworkproject.services;

import jakarta.servlet.http.Part;

import java.sql.Connection;
import java.util.List;

public interface PostPhotoService {

    void save(Connection conn, Long postId, List<Part> photos);

}
