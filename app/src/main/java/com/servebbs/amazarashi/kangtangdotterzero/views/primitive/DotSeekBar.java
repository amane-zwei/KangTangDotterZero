package com.servebbs.amazarashi.kangtangdotterzero.views.primitive;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;

public class DotSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {

    public DotSeekBar(Context context) {
        super(context);
        setProgressDrawable(new DotSeekBarDrawable(0xffff0000, 0xffffa0a0, getPaddingStart() + getPaddingEnd()));
    }

    static class DotSeekBarDrawable extends Drawable {

        private int mainColor;
        private int subColor;
        private int offset;

        public DotSeekBarDrawable(int mainColor, int subColor, int offset) {
            this.mainColor = mainColor;
            this.subColor = subColor;
            this.offset = offset;
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            final Rect bounds = canvas.getClipBounds();
            final int width = bounds.width() - offset;
            final int height = bounds.height();

            final int border = width * getLevel() / 10000;
            {
                Paint sub = new Paint();
                sub.setColor(mainColor);
                canvas.drawRect(0, 0, border, height, sub);
            }
            {
                Paint sub = new Paint();
                sub.setColor(subColor);
                canvas.drawRect(border, 0, width, height, sub);
            }

            Paint paint = new Paint();
            // left
            {
                Rect src = new Rect(32, 0, 48, 16);
                Rect dst = new Rect(0, 0, height, height);
                canvas.drawBitmap(DotIcon.getBitmap(), src, dst, paint);
            }
            // middle
            {
                Rect src = new Rect(40, 0, 56, 16);
                Rect dst = new Rect(height, 0, width - height, height);
                canvas.drawBitmap(DotIcon.getBitmap(), src, dst, paint);
            }
            // right
            {
                Rect src = new Rect(48, 0, 64, 16);
                Rect dst = new Rect(width - height, 0, width, height);
                canvas.drawBitmap(DotIcon.getBitmap(), src, dst, paint);
            }
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
