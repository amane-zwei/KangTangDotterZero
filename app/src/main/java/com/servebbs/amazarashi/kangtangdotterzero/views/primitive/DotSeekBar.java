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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;

public class DotSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {

    DotSeekBarDrawable drawable;

    public DotSeekBar(Context context) {
        super(context);

        drawable = new DotSeekBarDrawable(getPaddingStart() + getPaddingEnd());
        setProgressDrawable(drawable);
        setThumb(new DotSeekBarThumbDrawable());
    }

    public void setColor(int mainColor, int subColor) {
        drawable.setColor(mainColor, subColor);
    }

    static class DotSeekBarDrawable extends Drawable {

        private Paint paint;
        private Paint mainColorPaint;
        private Paint subColorPaint;
        private int offset;

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

            final int border = (width - offset) * getLevel() / 10000;

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

            tmpCanvas.drawRect(0, 0, border, height, mainColorPaint);
            tmpCanvas.drawRect(border, 0, width, height, subColorPaint);

            {
                Rect rect = new Rect(0, 0, tmpBitmap.getWidth(), tmpBitmap.getHeight());
                canvas.drawBitmap(tmpBitmap, rect, rect, paint);
            }

            drawBitmap(
                    canvas,
                    DotIcon.seekBar.getLeft(),
                    DotIcon.seekBar.getTop(),
                    DotIcon.seekBar.getWidth(),
                    DotIcon.seekBar.getHeight(),
                    offset,
                    paint
            );
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

    static class DotSeekBarThumbDrawable extends Drawable {

        private Paint paint;
        private Rect srcRect;

        public DotSeekBarThumbDrawable() {
            paint = new Paint();
            srcRect = DotIcon.seekBarThumb.createRect();
        }

        @Override
        public void draw(@NonNull Canvas canvas) {
            final Rect bounds = canvas.getClipBounds();
            canvas.drawBitmap(
                    DotIcon.getBitmap(),
                    srcRect,
                    getBounds(),
                    paint);
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

        @Override
        public int getIntrinsicWidth() {
            return ScreenSize.getIconSize() * DotIcon.seekBarThumb.getWidth() / 16;
        }

        @Override
        public int getIntrinsicHeight() {
            return ScreenSize.getIconSize();
        }
    }

}
