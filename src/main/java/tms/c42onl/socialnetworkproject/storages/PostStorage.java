package tms.c42onl.socialnetworkproject.storages;

import tms.c42onl.socialnetworkproject.models.Post;

import java.sql.Connection;

public interface PostStorage {

    Long save(Connection connection, Post post);

}
