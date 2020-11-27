package com.servebbs.amazarashi.kangtangdotterzero.models;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.KTDZApplication;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.ProjectContext;
import com.servebbs.amazarashi.kangtangdotterzero.models.tools.Tool;
import com.servebbs.amazarashi.kangtangdotterzero.models.tools.Pen;

import lombok.Getter;
import lombok.Setter;

public class GlobalContext {

    @Getter
    @Setter
    private Tool tool = new Pen();

    @Getter
    private boolean isMouseMode = false;

    @Getter
    private final ScreenNormalizer screenNormalizer = new ScreenNormalizer();

    public static GlobalContext get(Context context) {
        return KTDZApplication.get(context).getGlobalContext();
    }
}
