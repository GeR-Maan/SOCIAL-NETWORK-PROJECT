package tms.c42onl.socialnetworkproject.models.reactions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReactionCount {
    private long countLike = 0;         // Общее количество лайков под постом
    private long countDislike = 0;      // Общее количество дизлайков под постом
}
