package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.models.PostCard;

import java.util.List;

public interface PostFeedStorage {

    List<PostCard> findFeed();
}
