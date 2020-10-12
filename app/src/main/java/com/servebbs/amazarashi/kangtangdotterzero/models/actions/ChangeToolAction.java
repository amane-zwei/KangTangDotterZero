package com.servebbs.amazarashi.kangtangdotterzero.models.actions;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs.ColorPickerDialog;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.ProjectContext;
import com.servebbs.amazarashi.kangtangdotterzero.models.tools.Tool;

public class ChangeToolAction implements Action{

    private Tool tool;
    public ChangeToolAction(Tool tool) {
        this.tool = tool;
    }

    public void action(Context context) {
        ProjectContext.get(context).setTool(tool);
    }
}
