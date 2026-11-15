package tms.c42onl.socialnetworkproject.storages;

import lombok.Getter;
import tms.c42onl.socialnetworkproject.exceptions.ReactionException;
import tms.c42onl.socialnetworkproject.models.reactions.Reaction;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionCount;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionType;
import tms.c42onl.socialnetworkproject.service.ConnectionProvider;

import static tms.c42onl.socialnetworkproject.models.reactions.ReactionType.NOTHING;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Управление реакцией на посты в БД
public class ReactionStorage {
    @Getter
    private static final ReactionStorage reactionStorage = new ReactionStorage();

    private ReactionStorage() {}

    //Сохраняет или обновляет реакцию
    public void saveReaction(Reaction reaction) {
        // Если пользователь убрал реакцию, удаляем запись из БД
        if (reaction.getType() == NOTHING) {
            deleteReaction(reaction);
            return;
        }

        String sqlQuery = """
            INSERT INTO reactions (user_id, post_id, reaction_type)
            VALUES (?, ?, ?)
            ON CONFLICT (user_id, post_id) DO UPDATE SET reaction_type = EXCLUDED.reaction_type;
            """;

        try (Connection connection = ConnectionProvider.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.trim())) {
                preparedStatement.setLong(1, reaction.getUserId());
                preparedStatement.setLong(2, reaction.getPostId());
                preparedStatement.setString(3, reaction.getType().name());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ReactionException("Ошибка при сохранении реакции", e);
        }
    }


    //Удаляет реакцию на конкретный пост из БД
    public void deleteReaction(Reaction reaction) {
        String sqlQuery = """
                            DELETE FROM reactions 
                            WHERE user_id = ? AND post_id = ?;
                          """;
        try (Connection connection = ConnectionProvider.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.trim())) {
                preparedStatement.setLong(1, reaction.getUserId());
                preparedStatement.setLong(2, reaction.getPostId());
                preparedStatement.execute();
            }
        }catch (SQLException e){
            throw new ReactionException("Ошибка при удалении реакции", e);
        }
    }

    //Возвращает тип реакции на указанный пост
    public ReactionType getUserReaction(Long userId, Long postId){
        String sqlQuery = """
                            SELECT reaction_type
                            FROM reactions
                            WHERE user_id = ? AND post_id = ?;
                          """;

        try (Connection connection = ConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.trim())) {
                preparedStatement.setLong(1, userId);
                preparedStatement.setLong(2, postId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Если запись найдена, конвертируем строку из БД в Enum
                    return ReactionType.valueOf(resultSet.getString("reaction_type"));
                } else {
                    // Если записи нет, значит пользователь еще не оставлял реакцию
                    return NOTHING;
                }

        }catch (SQLException e){
            throw new ReactionException("Ошибка при получении типа реакции", e);
        }
    }

    //Возвращает общее количество лайков и дизлайков для указанного поста.
    public ReactionCount getCounters(Long postId) {
        String sqlQuery = """
                            SELECT
                            COUNT(CASE WHEN reaction_type = 'LIKE' THEN 1 END) AS count_like,
                            COUNT(CASE WHEN reaction_type = 'DISLIKE' THEN 1 END) AS count_dislike
                            FROM reactions
                            WHERE post_id = ?;
                          """;

        try (Connection connection = ConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.trim())) {

            preparedStatement.setLong(1, postId);

            ResultSet resultSet = preparedStatement.executeQuery();
            ReactionCount reactionCount = new ReactionCount();

            if (resultSet.next()) {
                reactionCount.setCountLike(resultSet.getLong("count_like"));
                reactionCount.setCountDislike(resultSet.getLong("count_dislike"));
            }

            return reactionCount;

        } catch (SQLException e) {
            throw new ReactionException("Ошибка при получении количества реакций", e);
        }
    }


    // Проверяет существование поста
    public boolean postExists(Long postId){
        String sqlQuery = """
                            SELECT COUNT(*) FROM posts WHERE id = ?
                          """;

        try (Connection connection = ConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.trim())) {
            preparedStatement.setLong(1,postId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong(1) > 0;
            }
            return false;
        }catch (SQLException e){
            throw new ReactionException("Ошибка при проверке существовании реакции", e);
        }
    }
}
