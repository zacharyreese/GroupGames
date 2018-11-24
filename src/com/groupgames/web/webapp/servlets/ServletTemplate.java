package com.groupgames.web.webapp.servlets;

import com.groupgames.web.core.DBManager;
import com.groupgames.web.core.FreemarkerManager;
import com.groupgames.web.webapp.AppContextListener;
import com.groupgames.web.webapp.ServletError;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServletTemplate extends HttpServlet {
    protected DBManager dbMgr;
    protected String webRootPath;

    public void init(ServletConfig servletconfig) throws ServletException {
        super.init(servletconfig);

        ServletContext ctx = servletconfig.getServletContext();

        this.webRootPath = ctx.getRealPath("/");

        try {
            this.dbMgr = (DBManager)     ctx.getAttribute(AppContextListener.DB_MNGR_KEY);
        } catch (ClassCastException e) {
            System.err.println("Failed to load required modules from webapp context");
            e.printStackTrace();
        }
    }

    void respondWithTemplate(HttpServletResponse resp, String templatePath, Map<String, Object> templateData) {
        try {
            Template template = FreemarkerManager.getInstance(webRootPath).getTemplate(templatePath);

            setBasicHeaders(resp);
            template.process(templateData, resp.getWriter());

        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void respondWithError(HttpServletResponse resp, String templatePath, ServletError error){
        Map<String, Object> templateData = new HashMap<>();
        ServletError.setError(templateData, error);
        respondWithTemplate(resp, templatePath, templateData);
    }

    private void setBasicHeaders(HttpServletResponse resp) {
        resp.setContentType("text/html");
    }
}
