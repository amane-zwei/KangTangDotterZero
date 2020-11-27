package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;

public class CursorView extends SimpleIconView{
    public CursorView(Context context) {
        super(context);

        setClickable(false);
        setRect(DotIcon.cursor.createRect());
    }
}
