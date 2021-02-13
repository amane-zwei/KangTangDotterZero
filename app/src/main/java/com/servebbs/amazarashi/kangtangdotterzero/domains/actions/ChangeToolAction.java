package com.servebbs.amazarashi.kangtangdotterzero.domains.actions;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.MainActivity;
import com.servebbs.amazarashi.kangtangdotterzero.domains.GlobalContext;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;
import com.servebbs.amazarashi.kangtangdotterzero.domains.tools.Tool;

import lombok.Setter;

public class ChangeToolAction implements Action {

    @Setter
    private Tool tool;

    public DotIcon.DotIconData getIcon() {
        return tool.getIcon();
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
