package tms.c42onl.socialnetworkproject.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tms.c42onl.socialnetworkproject.exceptions.ReactionException;
import tms.c42onl.socialnetworkproject.models.reactions.Reaction;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionCount;
import tms.c42onl.socialnetworkproject.models.reactions.ReactionType;
import tms.c42onl.socialnetworkproject.storages.ReactionStorage;

// Сервис для работы с реакциями
public class ReactionService {

    private final ReactionStorage reactionStorage;

    public ReactionService(ReactionStorage reactionStorage) {
        this.reactionStorage = reactionStorage;
    }

    // Изменяет или устанавливает реакцию на пост
    public boolean changeReaction(Long userId, Long postId, String reactionTypeString) {
        // Проверка на существование поста
        if (!reactionStorage.postExists(postId)){
            return false;
        }

        ReactionType reactionType;
        try {
            reactionType = ReactionType.valueOf(reactionTypeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ReactionException("Ошибка при установке/изменении реакции", e);
        }
        if(reactionType == reactionStorage.getUserReaction(userId, postId)) {
            reactionType = ReactionType.NOTHING;
        }

        Reaction reaction = new Reaction(null, userId, postId, reactionType);

        reactionStorage.saveReaction(reaction);
        return true;
    }

    //Получает текущую реакцию пользователя
    public ReactionType getUserReaction(Long userId, Long postId){
        return reactionStorage.getUserReaction(userId, postId);
    }

    //Получает количество реакций
    public ReactionCount getCounters(Long postId){
            return reactionStorage.getCounters(postId);
    }
}
