package com.servebbs.amazarashi.kangtangdotterzero.views.primitive;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotFont;

public class DotTextView extends androidx.appcompat.widget.AppCompatTextView {

    public DotTextView(Context context) {
        super(context);

        setTypeface(DotFont.getDotTypeface());
        setIncludeFontPadding(false);
        setTextSize(DotFont.normalHeight);
    }
}
