package com.servebbs.amazarashi.kangtangdotterzero.models.actions;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs.SaveProjectDialog;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;

public class CallSaveProjectDialogAction implements Action {
    public void action(Context context) {
        final Project project = Project.get(context);
        new SaveProjectDialog()
                .attachProject(project)
                .show(((AppCompatActivity) context).getSupportFragmentManager(), "action_save_project");
    }
}
