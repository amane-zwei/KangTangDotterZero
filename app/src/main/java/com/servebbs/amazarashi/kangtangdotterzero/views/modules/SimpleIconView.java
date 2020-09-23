package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;

public class SimpleIconView extends View {
    private final static Paint paint = new Paint();

    private Rect rect;

    public SimpleIconView(Context context) {
        super(context);
    }

    public SimpleIconView setRect(Rect rect) {
        this.rect = rect;
        return this;
    }

    @Override
    public void onDraw(Canvas canvas) {
        final Rect dstRect = canvas.getClipBounds();
        canvas.drawBitmap(DotIcon.getBitmap(), rect, dstRect, paint);
    }
}
