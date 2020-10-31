package com.servebbs.amazarashi.kangtangdotterzero.views;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.servebbs.amazarashi.kangtangdotterzero.KTDZApplication;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.ProjectContext;
import com.servebbs.amazarashi.kangtangdotterzero.views.menu.HorizontalMenuView;

import lombok.Getter;

public class MainView extends FrameLayout {

    @Getter
    private ProjectView projectView;

    public MainView(Context context) {
        super(context);

        addProjectView(context);
    }

    private void addProjectView(Context context){
        ProjectView projectView = this.projectView = new ProjectView(context, ((KTDZApplication)context.getApplicationContext()).getProjectContext());
        projectView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        );
        this.addView(projectView);

        HorizontalMenuView menuView = new HorizontalMenuView(context);
        menuView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM)
        );
        this.addView(menuView);
    }
}
