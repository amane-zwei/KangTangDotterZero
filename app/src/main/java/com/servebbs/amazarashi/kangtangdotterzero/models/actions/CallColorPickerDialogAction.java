package com.servebbs.amazarashi.kangtangdotterzero.models.actions;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.servebbs.amazarashi.kangtangdotterzero.fragments.MainFragment;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs.ColorPickerDialog;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Palette;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;

public class CallColorPickerDialogAction implements Action{
    public void action(Context context) {
        final Project project = Project.get(context);
        new ColorPickerDialog()
                .attachPalette(new Palette(project.getPalette()))
                .setOnPositive((Palette palette) -> {
                    project.applyPalette(palette);
                    MainFragment.get(context).getMainView().invalidateProjectViews();
                })
                .show(((AppCompatActivity) context).getSupportFragmentManager(), "action_color_picker");
    }
}
