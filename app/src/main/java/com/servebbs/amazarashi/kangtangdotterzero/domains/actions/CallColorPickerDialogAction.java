package com.servebbs.amazarashi.kangtangdotterzero.domains.actions;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.MainFragment;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs.ColorPickerDialog;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Palette;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;

public class CallColorPickerDialogAction implements Action{
    public DotIcon.DotIconData getIcon() { return DotIcon.colorPicker; }

    public void action(Context context) {
        final Project project = Project.get(context);
        new ColorPickerDialog()
                .attachPalette(new Palette(project.getPalette()))
                .setOnPositive((Palette palette) -> {
                    project.applyPalette(palette);
                    MainFragment.get(context).getMainView().invalidateProjectViews();
                    return true;
                })
                .show(((AppCompatActivity) context).getSupportFragmentManager(), "action_color_picker");
    }
}
