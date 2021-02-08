package com.servebbs.amazarashi.kangtangdotterzero.views.drawer;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.views.primitive.DotTextView;

public class DrawerItemView extends DotTextView {
    private static final int padding = ScreenSize.getDotSize() * 2;
    private static final int indent = ScreenSize.getIconSize();

    public DrawerItemView(Context context) {
        super(context);

        setTextColor(0xff000000);
        setBackgroundColor(0xffffffff);
    }

    public void setLevel(int level) {
        setPadding(level * indent, padding, 0, padding);
    }
}
