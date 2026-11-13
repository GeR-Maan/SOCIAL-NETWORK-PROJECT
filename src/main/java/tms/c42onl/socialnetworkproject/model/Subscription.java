package tms.c42onl.socialnetworkproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    private Long id;
    private Long subscriberId;
    private Long authorId;
    private LocalDateTime createdAt;

    public Subscription(Long subscriberId, Long authorId) {
        this.subscriberId = subscriberId;
        this.authorId = authorId;
    }
}
