package com.servebbs.amazarashi.kangtangdotterzero.domains.actions;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;

public interface Action {
    void action(Context context);

    DotIcon.DotIconData getIcon();
}
