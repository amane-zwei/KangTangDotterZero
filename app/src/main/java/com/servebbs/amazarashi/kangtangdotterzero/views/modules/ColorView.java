package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.ScrollView;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;

public class ColorView extends View {
    private final static Paint paint = new Paint();
    private final static Rect srcRect = DotIcon.pallet.createRect();

    private final Paint basePaint = new Paint();

    public ColorView(Context context) {
        super(context);
    }

    public void setColor(int color) {
        basePaint.setColor(color);
    }

    @Override
    public void onDraw(Canvas canvas) {
        final Rect dstRect = canvas.getClipBounds();
        final int dotSize = ScreenSize.getDotSize() * 2;

        canvas.drawRect(
                new Rect(
                        dstRect.left + dotSize,
                        dstRect.top + dotSize,
                        dstRect.right - dotSize,
                        dstRect.bottom - dotSize),
                basePaint);
        canvas.drawBitmap(DotIcon.getBitmap(), srcRect, dstRect, paint);
    }
}
