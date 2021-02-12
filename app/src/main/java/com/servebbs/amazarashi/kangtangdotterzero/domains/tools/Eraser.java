package com.servebbs.amazarashi.kangtangdotterzero.domains.tools;

import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotColor;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;

public class Eraser extends Pen {
    @Override
    public DotIcon.DotIconData getIcon() { return DotIcon.eraser; }

    @Override
    protected DotColor getColor(Project project) {
        return project.getClearColor();
    }
}
