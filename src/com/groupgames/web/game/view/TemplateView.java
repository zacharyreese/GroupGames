package com.groupgames.web.game.view;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Writer;

public class TemplateView implements View {
    private String templatePath;
    private Object templateData;
    private Template template;

    /**
     * TemplateView is a View which processes the Template loaded from the specified
     * @templatePath with the @templateData
     *
     * @param templatePath path of the template to load
     * @param templateData data to be inserted into the template
     */
    public TemplateView(String templatePath, Object templateData) {
        this.setTemplate(templatePath);
        this.templateData = templateData;
    }

    /**
     * Loads and sets the corresponding FreeMarker path for the given view
     *
     */
    public void setTemplate(String path) /* throws cant find template? */ {
        templatePath = path;

    }

    @Override
    public boolean respond(Writer out) {

        try {
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
