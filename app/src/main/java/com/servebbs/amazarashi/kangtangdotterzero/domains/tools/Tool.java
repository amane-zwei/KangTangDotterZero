package com.servebbs.amazarashi.kangtangdotterzero.domains.tools;

import android.graphics.Rect;

import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;

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
