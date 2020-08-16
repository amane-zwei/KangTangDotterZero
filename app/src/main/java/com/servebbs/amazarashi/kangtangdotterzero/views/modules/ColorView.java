package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;
import android.view.View;
import android.widget.ScrollView;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;

public class ColorView extends View {
    private int color = 0xffffa0a0;

    public ColorView(Context context) {
        super(context);
    }

    public void setColor(int color) {
        this.color = color;
        setBackgroundColor(color);
    }
}
