package com.servebbs.amazarashi.kangtangdotterzero.domains.actions;

import android.content.Context;

import androidx.core.view.GravityCompat;

import com.servebbs.amazarashi.kangtangdotterzero.MainActivity;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;

public class CallDrawerAction implements Action {
    public DotIcon.DotIconData getIcon() { return DotIcon.pen; }

    public void action(Context context) {
        ((MainActivity) context)
                .getDrawer()
                .openDrawer(GravityCompat.START);
    }
}
