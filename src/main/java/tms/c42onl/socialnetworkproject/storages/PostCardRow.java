package tms.c42onl.socialnetworkproject.storages;

import java.time.LocalDateTime;

public record PostCardRow(
        long id,
        long authorId,
        String text,
        LocalDateTime createdAt,
        String authorDisplayName,
        String authorAvatarUrl
) {
}
