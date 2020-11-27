package com.servebbs.amazarashi.kangtangdotterzero.models.project;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.servebbs.amazarashi.kangtangdotterzero.KTDZApplication;
import com.servebbs.amazarashi.kangtangdotterzero.models.GlobalContext;
import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenNormalizer;

import lombok.Getter;

public class ProjectContext {

    @Getter
    private final Project project = new Project();

    public Layer getLayer() {
        return project.getFrame().getLayer();
    }

    public boolean onTouch(int x, int y, int action) {
        return true;
    }

    public static ProjectContext get(Context context) {
        return KTDZApplication.get(context).getProjectContext();
    }
}
