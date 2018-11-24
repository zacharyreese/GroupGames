package com.groupgames.web.game.view;

import com.groupgames.web.core.FreemarkerManager;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Writer;

public class TemplateView implements View {
    private String webRootPath;
    private String templatePath;
    private Object templateData;

    /**
     * TemplateView is a View which processes the Template loaded from the specified
     * @templatePath with the @templateData
     *
     * @param templatePath path of the template to load
     * @param templateData data to be inserted into the template
     */
    public TemplateView(String webRootPath, String templatePath, Object templateData) throws IOException {
        this.webRootPath = webRootPath;
        this.templatePath = templatePath;
        this.templateData = templateData;
    }

    @Override
    public boolean respond(Writer out) {

        try {
            // Attempt to load the template
            Template template = FreemarkerManager.getInstance(webRootPath).getTemplate(templatePath);
            // Attempt to process the template
            template.process(templateData, out);
        } catch (TemplateException | IOException e) {
            // Failed to process the template or write it to the output
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
