package com.servebbs.amazarashi.kangtangdotterzero.models;

import com.servebbs.amazarashi.kangtangdotterzero.models.tools.Tool;
import com.servebbs.amazarashi.kangtangdotterzero.models.tools.Pen;

import lombok.Getter;
import lombok.Setter;

public class GlobalContext {

    @Getter
    @Setter
    private Tool tool = new Pen();
    private ScreenNormalizer screenNormalizer = new ScreenNormalizer();

    public ScreenNormalizer getNormalizer() { return screenNormalizer; }
}
