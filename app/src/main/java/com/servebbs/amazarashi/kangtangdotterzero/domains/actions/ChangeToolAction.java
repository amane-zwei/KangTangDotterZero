package com.servebbs.amazarashi.kangtangdotterzero.domains.actions;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.MainActivity;
import com.servebbs.amazarashi.kangtangdotterzero.domains.GlobalContext;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;
import com.servebbs.amazarashi.kangtangdotterzero.domains.tools.Tool;

public class ChangeToolAction implements Action {

    private final Tool tool;

    public ChangeToolAction(Tool tool) {
        this.tool = tool;
    }

    public void action(Context context) {
        GlobalContext globalContext = GlobalContext.get(context);
        globalContext.getTool().flush(new Tool.Event(
                Project.get(context),
                0,
                0,
                0
        ));
        globalContext.setTool(tool);
        ((MainActivity) context).getToolViews().invalidateViews();
    }
}
