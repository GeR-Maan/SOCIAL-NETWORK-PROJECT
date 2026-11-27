package tms.c42onl.socialnetworkproject.servlets;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;
import org.flywaydb.core.Flyway;
import tms.c42onl.socialnetworkproject.config.ConnectionProvider;
import tms.c42onl.socialnetworkproject.config.DatabaseConfig;
import tms.c42onl.socialnetworkproject.config.JDBCManager;

import tms.c42onl.socialnetworkproject.storages.CommentStorage;
import tms.c42onl.socialnetworkproject.storages.JdbcCommentStorage;
import tms.c42onl.socialnetworkproject.services.CommentService;
import tms.c42onl.socialnetworkproject.services.DefaultPostFeedService;
import tms.c42onl.socialnetworkproject.services.PostFeedService;
import tms.c42onl.socialnetworkproject.storages.JdbcPostFeedStorage;
import tms.c42onl.socialnetworkproject.storages.JdbcPostImageStorage;
import tms.c42onl.socialnetworkproject.storages.PostFeedStorage;
import tms.c42onl.socialnetworkproject.storages.ReactionStorage;
import tms.c42onl.socialnetworkproject.service.ReactionService;

import java.util.Collection;

@WebListener
public class ServletContextListener implements jakarta.servlet.ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseConfig config = DatabaseConfig.databaseConfig();

        Flyway.configure()
                .dataSource(
                        config.getUrl(),
                        config.getUsername(),
                        config.getPassword()
                )
                .load()
                .migrate();

        ConnectionProvider provider = new ConnectionProvider(config);
        JDBCManager jdbcManager = new JDBCManager(provider);
        sce.getServletContext().setAttribute("provider", provider);
        sce.getServletContext().setAttribute("connection", jdbcManager);

        CommentStorage commentStorage = new JdbcCommentStorage(provider);
        CommentService commentService = new CommentService(commentStorage);
        sce.getServletContext().setAttribute("commentService", commentService);
        ReactionStorage reactionStorage = new ReactionStorage(provider);
        ReactionService reactionService = new ReactionService(reactionStorage);
        sce.getServletContext().setAttribute("reactionService", reactionService);

        PostFeedStorage feedStorage = new JdbcPostFeedStorage(provider);
        PostFeedService feedService = new DefaultPostFeedService(
                feedStorage, jdbcManager, new JdbcPostImageStorage(),
                reactionService, commentService
        );
        sce.getServletContext().setAttribute("postFeedService", feedService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("connection");
        sce.getServletContext().removeAttribute("provider");
        sce.getServletContext().removeAttribute("commentService");
        sce.getServletContext().removeAttribute("reactionService");
        sce.getServletContext().removeAttribute("postFeedService");
    }
}
