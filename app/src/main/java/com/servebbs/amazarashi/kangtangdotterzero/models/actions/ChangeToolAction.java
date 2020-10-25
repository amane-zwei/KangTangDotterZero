package com.servebbs.amazarashi.kangtangdotterzero.models.actions;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.MainActivity;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.ProjectContext;
import com.servebbs.amazarashi.kangtangdotterzero.models.tools.Tool;

public class ChangeToolAction implements Action {

    private Tool tool;

    public ChangeToolAction(Tool tool) {
        this.tool = tool;
    }

    public void action(Context context) {
        ProjectContext.get(context).setTool(tool);
        ((MainActivity) context).getToolViews().invalidateViews();
    }
}