package tms.c42onl.socialnetworkproject.Services;

import tms.c42onl.socialnetworkproject.models.PostPage;

public interface PostDetailsService {
    PostPage getPostDetails(long postId, Long currentUserId);
}