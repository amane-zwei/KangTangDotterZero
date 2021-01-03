package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.servebbs.amazarashi.kangtangdotterzero.models.files.Extension;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;

public class ThumbnailView extends View {
    private final static Paint paint = new Paint();
    private final static Paint subPaint;
    static {
        subPaint = new Paint();
        subPaint.setColor(0xff000000);
    }

    private int margin;
    private Rect rect;
    private Extension extension;
    private Bitmap bitmap = null;

    public ThumbnailView(Context context) {
        super(context);
    }

    public ThumbnailView setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    public ThumbnailView setExtension(boolean isDirectory, Extension extension) {
        this.extension = extension;
        if (bitmap != null) {
            return this;
        }
        if (isDirectory) {
            this.rect = DotIcon.load.createRect();
        } else if (extension == Extension.KTDZ_PROJECT) {
            this.rect = DotIcon.omochi.createRect();
        } else {
            this.rect = DotIcon.picture.createRect();
        }
        return this;
    }

    public ThumbnailView setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        if (bitmap != null) {
            this.rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
        return this;
    }

    @Override
    public void onDraw(Canvas canvas) {
        final Rect dstSubRect = canvas.getClipBounds();
        final Rect dstRect = new Rect(
                dstSubRect.left + margin,
                dstSubRect.top + margin,
                dstSubRect.right - margin,
                dstSubRect.bottom - margin);

        if (bitmap == null) {
            canvas.drawBitmap(DotIcon.getBitmap(), rect, dstRect, paint);
        } else {
            canvas.drawRect(dstSubRect, subPaint);
            canvas.drawBitmap(bitmap, rect, dstRect, paint);
        }
    }
}
