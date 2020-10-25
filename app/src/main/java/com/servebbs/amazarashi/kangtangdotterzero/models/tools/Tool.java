package com.servebbs.amazarashi.kangtangdotterzero.models.tools;

import android.graphics.Rect;
import android.view.MotionEvent;

import com.servebbs.amazarashi.kangtangdotterzero.models.project.ProjectContext;

public class Tool {
    public boolean touch(MotionEvent event, ProjectContext projectContext) {
        return false;
    }
    public Rect createIconRect() { return new Rect(); }
}
