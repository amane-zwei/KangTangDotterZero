package com.servebbs.amazarashi.kangtangdotterzero;

import android.app.Application;

import com.servebbs.amazarashi.kangtangdotterzero.models.GlobalContext;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.ProjectContext;

public class KTDZApplication extends Application {

    GlobalContext globalContext;
    ProjectContext projectContext;

    @Override
    public void onCreate() {
        super.onCreate();

        projectContext = new ProjectContext();
        globalContext = new GlobalContext();

        android.util.Log.d("hogehoge", "on create!");
    }

    public ProjectContext getProjectContext() { projectContext.setGlobalContext(globalContext); return projectContext; }
}
