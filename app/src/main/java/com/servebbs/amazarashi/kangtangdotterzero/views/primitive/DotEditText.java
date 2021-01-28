package com.servebbs.amazarashi.kangtangdotterzero.views.primitive;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;

import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;

public class DotEditText extends androidx.appcompat.widget.AppCompatEditText {

    public DotEditText(Context context) {
        super(context);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setStroke(ScreenSize.getIconSize()/16, 0xff000000);
        setBackground(gradientDrawable);
    }
}
