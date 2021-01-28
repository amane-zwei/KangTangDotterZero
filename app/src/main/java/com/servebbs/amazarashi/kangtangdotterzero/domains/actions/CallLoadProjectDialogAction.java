package com.servebbs.amazarashi.kangtangdotterzero.domains.actions;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.servebbs.amazarashi.kangtangdotterzero.fragments.MainFragment;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs.LoadProjectDialog;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.ProjectContext;

public class CallLoadProjectDialogAction implements Action {
    public void action(Context context) {
        new LoadProjectDialog()
                .setOnPositive((Project project) -> {
                    ProjectContext.get(context).applyProject(project);
                    MainFragment.get(context).getMainView().applyProject(project);
                    return true;
                })
                .show(((AppCompatActivity) context).getSupportFragmentManager(), "action_load_project");
    }
}
