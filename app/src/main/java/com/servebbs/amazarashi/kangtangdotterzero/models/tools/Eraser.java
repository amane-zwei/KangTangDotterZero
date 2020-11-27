package com.servebbs.amazarashi.kangtangdotterzero.models.tools;

import android.graphics.Rect;

import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;

public class Eraser extends Pen {
    @Override
    public Rect createIconRect() {
        return DotIcon.eraser.createRect();
    }

    @Override
    protected int getColor(Project project) {
        return 0x0;
    }

    @Override
    protected int getColorIndex(Project project) {
        return 0x0;
    }
}
