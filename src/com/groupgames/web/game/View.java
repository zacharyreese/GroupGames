package com.groupgames.web.game;

public abstract class View {
    private String templatePath;

    public View(String templatePath){
        this.templatePath = templatePath;
    }

    /**
     * Returns a corresponding FreeMarker template path for the given view.
     *
     * @return template path relative to the template root
     */
    public String getTemplatePath() {
        return templatePath;
    }
}
