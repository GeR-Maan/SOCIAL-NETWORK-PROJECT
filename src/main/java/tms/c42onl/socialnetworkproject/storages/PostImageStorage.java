package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.models.PostImage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface PostImageStorage {
    void save(Connection conn, long postId, List<PostImage> images) throws SQLException;
    List<PostImage> findByPostId(Connection conn, long postId) throws SQLException;
}
