package com.servebbs.amazarashi.kangtangdotterzero.views.primitive;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DotButton extends androidx.appcompat.widget.AppCompatButton {

    public DotButton(Context context) {
        super(context);

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_focused}, new DotButtonDrawable(0xffa000));
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new DotButtonDrawable(0xff0000));
        stateListDrawable.addState(new int[0], new DotButtonDrawable(0xffffff));
        setBackground(stateListDrawable);
    }

    public static class DotButtonDrawable extends Drawable {
        int alpha = 0xff;
        int color;

        DotButtonDrawable(int color) {
            this.color = color;
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            canvas.drawColor((alpha << 24) + color);
        }

        @Override
        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {
        }

        @Override
        public int getOpacity() {
            return PixelFormat.OPAQUE;
        }
    }
}
