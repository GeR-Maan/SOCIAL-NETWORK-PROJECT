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
    private long countLike = 0;
    private long countDislike = 0;
}
