package com.servebbs.amazarashi.kangtangdotterzero.domains.actions;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.ProjectContext;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.MainFragment;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs.NewProjectDialog;

public class CallNewProjectDialogAction implements Action {
    public DotIcon.DotIconData getIcon() {
        return DotIcon.newPaper;
    }

    public void action(Context context) {
        final Project currentProject = Project.get(context);
        new NewProjectDialog()
                .applyProject(currentProject)
                .setOnPositive((Project newProject) -> {
                    ProjectContext.get(context).applyProject(newProject);
                    MainFragment.get(context).getMainView().applyProject(newProject);
                    return true;
                })
                .show(((AppCompatActivity) context).getSupportFragmentManager(), "action_new_project");
    }
}
