package com.servebbs.amazarashi.kangtangdotterzero.models;

import com.servebbs.amazarashi.kangtangdotterzero.models.tools.Tool;
import com.servebbs.amazarashi.kangtangdotterzero.models.tools.Pen;

public class GlobalContext {

    private Pen pen = new Pen();
    private ScreenNormalizer screenNormalizer = new ScreenNormalizer();

    public Tool getTool() { return pen; }
    public ScreenNormalizer getNormalizer() { return screenNormalizer; }
}
