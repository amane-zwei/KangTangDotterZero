package com.servebbs.amazarashi.kangtangdotterzero.views.drawer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;

public class DrawerMenuView extends LinearLayout {

    private static final int titleHeight = ScreenSize.getIconSize() * 3;

    private final DrawerListView drawerListView;

    public DrawerMenuView(Context context) {
        super(context);

        setOrientation(LinearLayout.VERTICAL);
        setBackgroundColor(0xffffffff);

        {
            TitleView titleView = new TitleView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    titleHeight
            );
            titleView.setLayoutParams(layoutParams);
            addView(titleView);
        }
        {
            DrawerListView drawerListView = this.drawerListView = new DrawerListView(context);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    0,
                    1
            );
            drawerListView.setLayoutParams(layoutParams);
            addView(drawerListView);
        }
    }

    public void setItemClickListener(DrawerListView.OnItemClick onItemClickListener) {
        drawerListView.setOnItemClick(onItemClickListener);
    }

    private static class TitleView extends View {
        private TitleView(Context context) {
            super(context);

            setBackgroundColor(0xffd0d0ff);
        }
    }

}
