package com.servebbs.amazarashi.kangtangdotterzero.models.tools;

import android.graphics.Rect;

import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.ProjectContext;

public class Eraser extends Pen {
    @Override
    public Rect createIconRect() {
        return DotIcon.eraser.createRect();
    }

    @Override
    public int getColor(ProjectContext context) {
        return 0x0;
    }

    @Override
    public int getColorIndex(ProjectContext context) { return 0x0; }
}
