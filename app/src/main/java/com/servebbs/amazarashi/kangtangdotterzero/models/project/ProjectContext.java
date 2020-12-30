package com.servebbs.amazarashi.kangtangdotterzero.models.project;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.KTDZApplication;

import lombok.Getter;

public class ProjectContext {

    @Getter
    private Project project = Project.create();

    public void applyProject(Project project) {
        this.project = project;
    }

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
