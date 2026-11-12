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
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("connection");
        sce.getServletContext().removeAttribute("provider");
        sce.getServletContext().removeAttribute("commentService");
    }
}
