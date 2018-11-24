package com.groupgames.web.core;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class FreemarkerManager {
    // Template directory relative to the webRootPath
    private static final String templateDir = "WEB-INF/templates/";

    // Singleton instance
    private static FreemarkerManager freeMarkerManager;

    private Configuration ftlConfig;

    /**
     * Private constructor for singleton instance
     * @param webRootPath
     */
    private FreemarkerManager(String webRootPath){
        String realTemplatePath = Paths.get(webRootPath, templateDir).toString();
        this.ftlConfig = initFreemarker(realTemplatePath);
    }

    /**
     * FreeMarker configuration requires the webRootPath to reference web files
     *
     * @param webRootPath
     * @return
     */
    public static FreemarkerManager getInstance(String webRootPath) {
        if  (freeMarkerManager == null)
            freeMarkerManager = new FreemarkerManager(webRootPath);

        return freeMarkerManager;
    }

    /**
     * Lookup and return a Template using the provided @templatePath
     *
     * @param templatePath
     * @throws IOException
     * @return Template located by path
     */
    public Template getTemplate(String templatePath) throws IOException {
        Template template = null;

        template = ftlConfig.getTemplate(templatePath);

        return template;
    }

    /**
     * Initialize the Freemarker configuration from the computed absolute path of the template
     * directory
     *
     * @param realTemplatePath
     * @return Freemarker Configuration object
     */
    private Configuration initFreemarker(String realTemplatePath) {
        Configuration freeCfg = new Configuration(Configuration.VERSION_2_3_0);

        try {
            File file = new File(realTemplatePath);
            freeCfg.setDirectoryForTemplateLoading(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        freeCfg.setDefaultEncoding("UTF-8");
        freeCfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        freeCfg.setLogTemplateExceptions(false);
        freeCfg.setWrapUncheckedExceptions(true);

        return freeCfg;
    }
}
