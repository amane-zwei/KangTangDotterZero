package com.servebbs.amazarashi.kangtangdotterzero.views.primitive;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;

import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotFont;

public class DotEditText extends androidx.appcompat.widget.AppCompatEditText {

    public DotEditText(Context context) {
        super(context);

        setTypeface(DotFont.getDotTypeface());
        setIncludeFontPadding(false);

        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setStroke(ScreenSize.getDotSize(), 0xff000000);
        setBackground(gradientDrawable);
    }
}
