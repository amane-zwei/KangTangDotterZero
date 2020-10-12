package com.servebbs.amazarashi.kangtangdotterzero.models.actions;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs.ColorPickerDialog;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;

public class CallColorPickerDialogAction implements Action{
    public void action(Context context) {
        final Project project = Project.get(context);
        new ColorPickerDialog()
                .attachPalette(project.getPalette().clone())
                .setOnPositive(project::applyPalette)
                .show(((AppCompatActivity) context).getSupportFragmentManager(), "action_color_picker");
    }
}
