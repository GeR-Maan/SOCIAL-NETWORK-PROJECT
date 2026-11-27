package tms.c42onl.socialnetworkproject.services;

import tms.c42onl.socialnetworkproject.models.PostCard;

import java.util.List;

public interface PostFeedService {

    List<PostCard> getFeed();
}
