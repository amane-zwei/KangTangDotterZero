package com.servebbs.amazarashi.kangtangdotterzero.views.primitive;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;

import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotFont;
import com.servebbs.amazarashi.kangtangdotterzero.drawables.DotRoundRectDrawable;

public class DotButton extends androidx.appcompat.widget.AppCompatButton {

    private static final int padding = ScreenSize.getDotSize() * 2;
    private static final int paddingRB = ScreenSize.getDotSize() * 3;

    private static final int[] stateFocused = {android.R.attr.state_focused};
    private static final int[] statePressed = {android.R.attr.state_pressed};
    private static final int[] stateNormal = new int[0];

    public DotButton(Context context) {
        super(context);

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(stateFocused, new DotRoundRectDrawable(0xffffa000, 0xff000000, 0x40000000));
        stateListDrawable.addState(statePressed, new DotRoundRectDrawable(0xffffa000, 0xff000000, 0x40000000));
        stateListDrawable.addState(stateNormal, new DotRoundRectDrawable(0xffffffff, 0xff000000, 0x40000000));
        setBackground(stateListDrawable);

        setTypeface(DotFont.getDotTypeface());
        setIncludeFontPadding(false);
        setTextSize(DotFont.normalHeight);
        setPadding(padding, padding, paddingRB, paddingRB);
    }
}
