package com.servebbs.amazarashi.kangtangdotterzero.models.tools;

import android.graphics.Rect;
import android.view.MotionEvent;

import com.servebbs.amazarashi.kangtangdotterzero.KTDZApplication;
import com.servebbs.amazarashi.kangtangdotterzero.models.histories.History;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.ProjectContext;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Tool {
    public boolean touch(Event event) {
        return false;
    }
    public void clear() {}
    public void flush(Event event) {}
    public Rect createIconRect() { return new Rect(); }

    @AllArgsConstructor
    @Getter
    public static class Event {
        private final Project project;
        private final int action;
        private final int x;
        private final int y;
    }
}
