package com.servebbs.amazarashi.kangtangdotterzero.domains.tools;

import android.graphics.Rect;

import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotColor;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;

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
