package tms.c42onl.socialnetworkproject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostImage {
        private Long id;
        private long postId;
        private String filePath;
        private int sortOrder;
}
