package com.servebbs.amazarashi.kangtangdotterzero.models.actions;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.servebbs.amazarashi.kangtangdotterzero.fragments.dialogs.LoadProjectDialog;

public class CallLoadProjectDialogAction implements Action {
    public void action(Context context) {
        new LoadProjectDialog()
                .show(((AppCompatActivity) context).getSupportFragmentManager(), "action_load_project");
    }
}
