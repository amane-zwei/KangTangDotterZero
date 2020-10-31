package com.servebbs.amazarashi.kangtangdotterzero.models.tools;

import android.graphics.Rect;
import android.view.MotionEvent;

import com.servebbs.amazarashi.kangtangdotterzero.models.histories.History;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.ProjectContext;

public class Tool {
    public boolean touch(MotionEvent event, ProjectContext projectContext) {
        return false;
    }
    public Rect createIconRect() { return new Rect(); }

    protected void addHistory(Project project, History history) {
        project.addHistory(history);
    }
}
