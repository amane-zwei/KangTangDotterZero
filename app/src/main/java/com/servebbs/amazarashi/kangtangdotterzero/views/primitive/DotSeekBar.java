package com.servebbs.amazarashi.kangtangdotterzero.views.primitive;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;

public class DotSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {
//    private static Paint black;
//    static {
//        black = new Paint();
//        black.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
//        black.setColor(0xff000000);
//    }

    DotSeekBarDrawable drawable;

    public DotSeekBar(Context context) {
        super(context);

        drawable = new DotSeekBarDrawable(getPaddingStart() + getPaddingEnd());
        setProgressDrawable(drawable);
        setThumb(new GradientDrawable());
    }

    public void setColor(int mainColor, int subColor) {
        drawable.setColor(mainColor, subColor);
    }

    static class DotSeekBarDrawable extends Drawable {

        private final Paint paint;
        private final Paint mainColorPaint;
        private final Paint subColorPaint;
        private final int offset;

        public DotSeekBarDrawable(int offset) {
            this.offset = offset;

            paint = new Paint();

            mainColorPaint = new Paint();
            mainColorPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));

            subColorPaint = new Paint();
            subColorPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        }

        public void setColor(int mainColor, int subColor) {
            mainColorPaint.setColor(mainColor);
            subColorPaint.setColor(subColor);
        }

        @Override
        public void draw(@NonNull Canvas canvas) {

            final Rect bounds = canvas.getClipBounds();
            final int width = bounds.width();
            final int height = bounds.height();

            final int dotSize = ScreenSize.getDotSize();

            final int border = (width - offset - dotSize * 3) * getLevel() / 10000 + dotSize;

            Bitmap tmpBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas tmpCanvas = new Canvas(tmpBitmap);

            drawBitmap(
                    tmpCanvas,
                    DotIcon.seekBar.getLeft() + 32,
                    DotIcon.seekBar.getTop(),
                    DotIcon.seekBar.getWidth(),
                    DotIcon.seekBar.getHeight(),
                    offset,
                    paint
            );

            tmpCanvas.drawRect(dotSize, 0, border, height, mainColorPaint);
            tmpCanvas.drawRect(border, 0, width, height, subColorPaint);
//            tmpCanvas.drawRect(border - dotSize / 2, 0, border + dotSize / 2, height, black);

            drawBitmap(
                    tmpCanvas,
                    DotIcon.seekBar.getLeft(),
                    DotIcon.seekBar.getTop(),
                    DotIcon.seekBar.getWidth(),
                    DotIcon.seekBar.getHeight(),
                    offset,
                    paint
            );

            {
                Rect rect = new Rect(0, 0, tmpBitmap.getWidth(), tmpBitmap.getHeight());
                canvas.drawBitmap(tmpBitmap, rect, rect, paint);
            }
        }

        private void drawBitmap(
                Canvas canvas,
                int left,
                int top,
                int width,
                int height,
                int offset,
                Paint paint) {
            final int bottom = top + height;

            final Rect bounds = canvas.getClipBounds();
            final int canvasWidth = bounds.width() - offset;
            final int canvasHeight = bounds.height();

            // left
            {
                Rect src = new Rect(
                        left,
                        top,
                        left + width / 2,
                        bottom);
                Rect dst = new Rect(0, 0, canvasHeight, canvasHeight);
                canvas.drawBitmap(DotIcon.getBitmap(), src, dst, paint);
            }
            // middle
            {
                Rect src = new Rect(
                        left + width / 4,
                        top,
                        left + width / 2,
                        bottom);
                Rect dst = new Rect(canvasHeight, 0, canvasWidth - canvasHeight, canvasHeight);
                canvas.drawBitmap(DotIcon.getBitmap(), src, dst, paint);
            }
            // right
            {
                Rect src = new Rect(
                        left + width / 2,
                        top,
                        left + width,
                        bottom);
                Rect dst = new Rect(canvasWidth - canvasHeight, 0, canvasWidth, canvasHeight);
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
