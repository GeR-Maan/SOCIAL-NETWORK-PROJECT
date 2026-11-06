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
    private Long id;
    private Long userId;
    private Long postId;
    private ReactionType type;
}
