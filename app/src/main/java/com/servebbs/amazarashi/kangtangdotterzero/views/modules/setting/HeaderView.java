package com.servebbs.amazarashi.kangtangdotterzero.views.modules.setting;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;

import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotFont;
import com.servebbs.amazarashi.kangtangdotterzero.views.primitive.DotTextView;

public class HeaderView extends DotTextView {

    private static final int textColor = 0xff000000;
    private static final int backgroundColor = 0xffffc0c0;

    private static final int paddingH = ScreenSize.getIconSize() / 4;
    private static final int paddingV = ScreenSize.getDotSize();

    public HeaderView(Context context) {
        super(context);

        setTextSize(TypedValue.COMPLEX_UNIT_PX, DotFont.normalHeight / 2f);
        setTextColor(textColor);
        setBackground(new ColorDrawable(backgroundColor));
        setPadding(paddingH, paddingV, paddingH, paddingV);
    }
}
