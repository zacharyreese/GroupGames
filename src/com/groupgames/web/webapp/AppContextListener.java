package com.groupgames.web.webapp;

import com.groupgames.web.core.DBManager;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.IOException;

@WebListener
public class AppContextListener
        implements ServletContextListener {

    // Key values for storing servlet utilities in the context
    public static final String FTL_CONFIG_KEY = "FTL_CONFIG";
    public static final String DB_MNGR_KEY    = "DB_MNGR";

    // Database configuration variables.
    // TODO: Move to config file
    private static final String dbUrl  = "jdbc:mysql://localhost/";
    private static final String dbName = "GroupGames";
    private static final String dbUser = "gg_server";
    private static final String dbPass = "password";

    // Template loading directory
    private static final String templateDir = "/WEB-INF/templates";

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("ServletContextListener destroyed");
    }

    //Run this before web application is started
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();

        initDBManager(ctx);
        initFreemarker(ctx);

        System.out.println("ServletContextListener started");
    }

    private void initDBManager(ServletContext ctx){
        DBManager dbMgr = new DBManager(dbUrl, dbName, dbUser, dbPass);
        ctx.setAttribute(DB_MNGR_KEY, dbMgr);
    }

    private void initFreemarker(ServletContext ctx) {
        Configuration freeCfg = new Configuration(Configuration.VERSION_2_3_0);

        try {
            File file = new File(ctx.getRealPath(templateDir));
            freeCfg.setDirectoryForTemplateLoading(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        freeCfg.setDefaultEncoding("UTF-8");
        freeCfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        freeCfg.setLogTemplateExceptions(false);
        freeCfg.setWrapUncheckedExceptions(true);

        ctx.setAttribute(FTL_CONFIG_KEY, freeCfg);
    }
}