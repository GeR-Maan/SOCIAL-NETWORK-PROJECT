package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.models.reactions.Reaction;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionCount;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionType;

import static tms.c42onl.socialnetworkproject.models.reactions.ReactionType.NOTHING;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ReactionStorage {
    //Сохранить/обновить реакцию
    public void saveReaction(Connection connection, Reaction reaction) {

        if (reaction.getType().equals(NOTHING)) {
            deleteReaction(connection, reaction);
            return;
        }

        String sqlQuery = """
                            INSERT INTO reactions (user_id, post_id, reaction_type)
                            VALUES (?, ?, ?)
                            ON CONFLICT (user_id, post_id)
                            DO UPDATE SET reaction_type = EXCLUDED.reaction_type;
                          """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.trim())){
            preparedStatement.setLong(1,reaction.getUserId());
            preparedStatement.setLong(2,reaction.getPostId());
            preparedStatement.setString(3, reaction.getType().toString());
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //Удалить реакцию
    public void deleteReaction(Connection connection, Reaction reaction) {
        String sqlQuery = """
                            DELETE FROM reactions 
                            WHERE user_id = ? AND post_id = ?;
                          """;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.trim())){
            preparedStatement.setLong(1,reaction.getUserId());
            preparedStatement.setLong(2,reaction.getPostId());
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    //Узнать реакцию текущего юзера.
    public ReactionType getUserReaction(Connection connection, Long userId, Long postId){
        String sqlQuery = """
                            SELECT reaction_type
                            FROM reactions
                            WHERE user_id = ? AND post_id = ?;
                          """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.trim())){
            preparedStatement.setLong(1,userId);
            preparedStatement.setLong(2,postId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return ReactionType.valueOf(resultSet.getString("reaction_type"));
            }
            else {
                return NOTHING;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    //Узнать счетчики (лайки/дизлайки)
    public ReactionCount getCounters(Connection connection, Long postId){
        String sqlQuery = """
                            SELECT
                                COUNT(CASE WHEN reaction_type = 'LIKE' THEN 1 END) AS count_like,
                                COUNT(CASE WHEN reaction_type = 'DISLIKE' THEN 1 END) AS count_dislike
                            FROM reactions
                            WHERE post_id = ?;
                          """;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.trim())){
            preparedStatement.setLong(1,postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            ReactionCount reactionCount = new ReactionCount();

            if (resultSet.next()) {
                reactionCount.setCountLike(resultSet.getLong("count_like"));
                reactionCount.setCountDislike(resultSet.getLong("count_dislike"));
                return reactionCount;
            }
            else {
                return reactionCount;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
