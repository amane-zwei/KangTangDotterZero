package com.servebbs.amazarashi.kangtangdotterzero.views.primitive;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.drawables.DividerDrawable;
import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;

public class Divider extends View {
    public Divider(Context context) {
        super(context);
        DividerDrawable drawable = new DividerDrawable(0xffa0a0ff);
        setBackground(drawable);
    }

    public Divider setLinearLayoutParams() {
        final int iconSize = ScreenSize.getIconSize();
        final int margin = ScreenSize.getPadding() * 2;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                iconSize / 4
        );
        layoutParams.setMargins(margin, 0, margin, 0);
        setLayoutParams(layoutParams);

        return this;
    }
}
