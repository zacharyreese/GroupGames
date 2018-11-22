package com.groupgames.web.game;

import java.io.Writer;

public interface View {

    /**
     * Write the view output to the client
     *
     * @param out output writer to write the processed output to
     * @return response successful
     */
    boolean respond(Writer out);
}
