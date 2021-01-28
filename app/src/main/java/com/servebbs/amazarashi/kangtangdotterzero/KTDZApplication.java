package com.servebbs.amazarashi.kangtangdotterzero;

import android.app.Application;
import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.domains.GlobalContext;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.ProjectContext;

import lombok.Getter;

public class KTDZApplication extends Application {

    @Getter
    GlobalContext globalContext;
    @Getter
    ProjectContext projectContext;

    @Override
    public void onCreate() {
        super.onCreate();

        projectContext = new ProjectContext();
        globalContext = new GlobalContext();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static KTDZApplication get(Context context) {
        return ((KTDZApplication)context.getApplicationContext());
    }
}
