package com.servebbs.amazarashi.kangtangdotterzero.models.tools;

import android.graphics.Rect;

import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotColor;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;

public class Eraser extends Pen {
    @Override
    public Rect createIconRect() {
        return DotIcon.eraser.createRect();
    }

    @Override
    protected DotColor getColor(Project project) {
        return project.getClearColor();
    }
}
