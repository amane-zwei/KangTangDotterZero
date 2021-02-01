package com.servebbs.amazarashi.kangtangdotterzero.domains.actions;

import android.content.Context;

import androidx.core.view.GravityCompat;

import com.servebbs.amazarashi.kangtangdotterzero.MainActivity;

public class CallDrawerAction implements Action {

    public void action(Context context) {
        ((MainActivity) context)
                .getDrawer()
                .openDrawer(GravityCompat.START);
    }
}
