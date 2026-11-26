package tms.c42onl.socialnetworkproject.models.reactions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Reaction {
    private Long id;            // id записи реакции в БД
    private Long userId;        // id пользователя оставившего реакцию
    private Long postId;        // id поста на который оставили реакцию
    private ReactionType type;  // тип поставленной реакции
}
