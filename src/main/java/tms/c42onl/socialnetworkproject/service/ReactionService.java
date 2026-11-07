package tms.c42onl.socialnetworkproject.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import tms.c42onl.socialnetworkproject.models.reactions.Reaction;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionType;
import tms.c42onl.socialnetworkproject.storages.ReactionStorage;

import java.sql.SQLException;
import java.sql.Connection;

@AllArgsConstructor
// Сервис для работы с реакциями
public class ReactionService {
    // Хранилище для работы с БД
    private final ReactionStorage reactionStorage;

    // Изменяет или устанавливает реакцию на пост
    public void changeReaction(Long userId, Long postId, String reactionTypeString) {
        //TODO: добавить проверку на существование поста

        ReactionType reactionType;
        try {
            reactionType = ReactionType.valueOf(reactionTypeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());      // TODO: Изменить обработку ошибки
            return;
        }

        Reaction reaction = new Reaction(null, userId, postId, reactionType);

        try(Connection connection = ConnectionManager.getConnection()){     // TODO: Изменить при выяснении установки
            reactionStorage.saveReaction(connection, reaction);
        } catch (SQLException e){
            e.printStackTrace();     // TODO: Изменить обработку ошибки
        }
    }
}
