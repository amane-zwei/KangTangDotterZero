package com.servebbs.amazarashi.kangtangdotterzero.views.modules.setting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.drawables.DotRoundRectDrawable;

public class SettingView extends LinearLayout {
    private static final int margin = ScreenSize.getPadding();
    private static final int padding = ScreenSize.getDotSize() * 2;

    public SettingView(Context context) {
        super(context);

        setOrientation(LinearLayout.VERTICAL);
    }

    public void addItem(String title, View view) {
        final Context context = getContext();

        {
            HeaderView headerView = new HeaderView(context);
            headerView.setText(title);
            LayoutParams layoutParams = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            headerView.setLayoutParams(layoutParams);
            addView(headerView);
        }
        {
            view.setBackground(new DotRoundRectDrawable());
            view.setPadding(
                    DotRoundRectDrawable.paddingLeft + padding,
                    DotRoundRectDrawable.paddingTop + padding,
                    DotRoundRectDrawable.paddingRight + padding,
                    DotRoundRectDrawable.paddingBottom + padding);

            LayoutParams layoutParams = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(margin, 0, margin, margin);
            view.setLayoutParams(layoutParams);
            addView(view);
        }
    }
}

