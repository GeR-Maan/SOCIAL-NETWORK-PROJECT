package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.models.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcPostStorage implements PostStorage {


    @Override
    public Long save(Connection connection, Post post) {

        String sql = "INSERT INTO posts (author_id,text) VALUES (?,?) RETURNING id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setLong(1, post.getAuthorId());
            preparedStatement.setString(2, post.getText());


            try(ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                return resultSet.getLong(1);
            }


        } catch (SQLException e) {
            throw new RuntimeException("Не удалось сохранить пост. ", e);
        }

    }
}
