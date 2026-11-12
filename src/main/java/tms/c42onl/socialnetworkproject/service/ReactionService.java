package tms.c42onl.socialnetworkproject.service;

import lombok.AllArgsConstructor;
import tms.c42onl.socialnetworkproject.exceptions.ReactionException;
import tms.c42onl.socialnetworkproject.models.reactions.Reaction;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionCount;
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
    public boolean changeReaction(Long userId, Long postId, String reactionTypeString) {
        try(Connection connection = ConnectionManager.getConnection()){     // TODO: Изменить при выяснении установки
            // Проверка на существование поста
            if (!reactionStorage.postExists(connection, postId)){
                return false;
            }

            ReactionType reactionType;
            try {
                reactionType = ReactionType.valueOf(reactionTypeString.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ReactionException("Ошибка при установке/изменении реакции", e);
            }
            if(reactionType == reactionStorage.getUserReaction(connection, userId, postId)) {
                reactionType = ReactionType.NOTHING;
            }

            Reaction reaction = new Reaction(null, userId, postId, reactionType);

            reactionStorage.saveReaction(connection, reaction);
            return true;
        } catch (SQLException e){
            throw new ReactionException("Ошибка при установке/изменении реакции", e);
        }
    }

    //Получает текущую реакцию пользователя
    public ReactionType getUserReaction(Long userId, Long postId){
        try(Connection connection = ConnectionManager.getConnection()){     // TODO: Изменить при выяснении установки
            return reactionStorage.getUserReaction(connection, userId, postId);
        } catch (SQLException e){
            throw new ReactionException("Ошибка при получении реакции", e);
        }
    }

    //Получает количество реакций
    public ReactionCount getCounters(Long postId){
        try(Connection connection = ConnectionManager.getConnection()){     // TODO: Изменить при выяснении установки
            return reactionStorage.getCounters(connection, postId);
        } catch (SQLException e){
            throw new ReactionException("Ошибка при получении количества реакции", e);
        }
    }
}
