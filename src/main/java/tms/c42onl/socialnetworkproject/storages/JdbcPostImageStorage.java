package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.models.PostImage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcPostImageStorage implements PostImageStorage {
    @Override
    public void save(Connection conn, long postId, List<PostImage> images) throws SQLException {
        String sql = "INSERT INTO post_images(post_id, file_path, sort_order) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int pos = 1;
            for (PostImage img : images) {
                ps.setLong(1, postId);
                ps.setString(2, img.getFilePath());
                ps.setInt(3, pos++);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public List<PostImage> findByPostId(Connection conn, long postId) throws SQLException {
        String sql = "SELECT id, post_id, file_path, sort_order FROM post_images " +
                "WHERE post_id = ? ORDER BY sort_order";
        List<PostImage> result = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PostImage img = new PostImage();
                    img.setId(rs.getLong("id"));
                    img.setPostId(rs.getLong("post_id"));
                    img.setFilePath(rs.getString("file_path"));
                    img.setSortOrder(rs.getInt("sort_order"));
                    result.add(img);
                }
            }
        }
        return result;
    }
}
