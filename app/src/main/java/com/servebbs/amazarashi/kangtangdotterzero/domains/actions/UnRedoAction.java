package com.servebbs.amazarashi.kangtangdotterzero.domains.actions;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.MainFragment;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.ProjectContext;

import lombok.Setter;

public class UnRedoAction implements Action {

    public static final int DELTA_UNDO = -1;
    public static final int DELTA_REDO = 1;

    @Setter
    private int delta;

    public DotIcon.DotIconData getIcon() { return DotIcon.undo; }

    public void action(Context context) {
        ProjectContext projectContext = ProjectContext.get(context);
        if (projectContext.getProject().unRedo(delta)) {
            MainFragment.get(context).getMainView().invalidateProjectViews();
        }
    }
}
