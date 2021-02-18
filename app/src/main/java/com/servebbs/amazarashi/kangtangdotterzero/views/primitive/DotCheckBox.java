package com.servebbs.amazarashi.kangtangdotterzero.views.primitive;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.servebbs.amazarashi.kangtangdotterzero.KTDZTheme;
import com.servebbs.amazarashi.kangtangdotterzero.domains.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotFont;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;

import lombok.Getter;
import lombok.Setter;

public class DotCheckBox extends androidx.appcompat.widget.AppCompatCheckBox {

    private static final int padding = ScreenSize.getDotSize() * 2;
    public static final int size = ScreenSize.getIconSize() / 2;
    private static final int textSize = DotFont.normalHeight / 2;

    public DotCheckBox(Context context) {
        super(context);

        CheckBoxDrawable drawable = new CheckBoxDrawable();
        drawable.setPadding(padding);
        drawable.setSize(size);
        setButtonDrawable(drawable);

        setTypeface(DotFont.getDotTypeface());
        setTextColor(KTDZTheme.textColor);
        setIncludeFontPadding(false);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    public static class CheckBoxDrawable extends Drawable {
        private static final Paint paint = new Paint();

        private static final Rect srcNoChecked = DotIcon.checkBox.createRect();
        private static final Rect srcChecked = DotIcon.checkBox.createRect(0, DotIcon.checkBox.getHeight());

        @Setter
        private int size;
        @Setter
        private int padding;
        @Getter
        private Rect src = srcChecked;
        @Getter
        private final Rect dst = new Rect();

        @Override
        public void onBoundsChange(Rect bounds) {
            dst.set(bounds.left + padding, bounds.top, bounds.right - padding, bounds.bottom);
        }

        @Override
        public int getIntrinsicWidth() {
            return size + padding + padding;
        }

        @Override
        public int getIntrinsicHeight() {
            return size;
        }

        @Override
        public boolean isStateful() {
            return true;
        }

        @Override
        public boolean onStateChange(int[] stateSet) {
            for (int state : stateSet) {
                if (state == android.R.attr.state_checked) {
                    Rect prev = src;
                    src = srcChecked;
                    return prev == src;
                }
            }
            Rect prev = src;
            src = srcNoChecked;
            return prev == src;
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            canvas.drawBitmap(DotIcon.getBitmap(), src, dst, paint);
        }

        @Override
        public void setAlpha(int alpha) {
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
