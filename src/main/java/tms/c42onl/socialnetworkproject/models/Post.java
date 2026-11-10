package tms.c42onl.socialnetworkproject.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    private Long id;
    private Long authorId;
    private String text;
    private LocalDateTime createdAt;

}
