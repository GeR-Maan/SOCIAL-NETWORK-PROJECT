package tms.c42onl.socialnetworkproject.models;

import java.time.LocalDateTime;
import java.util.List;

public record PostCard (
    long id,
    String preview,
    LocalDateTime createdAt,
    String permalink,
    AuthorView author,  //tms-29
    List<Photo> photos, //tms-29
    Counters counters
){
    public record AuthorView (long userId, String displayName, String avatarUrl){}  // заглушка из tms-29
    public record Photo (long id, String url, int width, int height){}  // PhotoView заглушка из tms-17
    public record Counters(long comments,long reactions){}
}

