package com.servebbs.amazarashi.kangtangdotterzero.domains.actions;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.MainFragment;

public class FlipCursorModeAction implements Action {
    public DotIcon.DotIconData getIcon() {
        return DotIcon.flipCursor;
    }

    public void action(Context context) {
        MainFragment.get(context)
                .getMainView()
                .flipCursor();
    }
}
