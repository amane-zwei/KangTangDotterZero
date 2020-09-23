package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenSize;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Palette;

import lombok.Setter;

public class ColorView extends View {
    private final static Paint paint = new Paint();
    private final static Rect selectedRect = DotIcon.pallet.createRect();
    private final static Rect normalRect = DotIcon.cursor.createRect();
    private final static Rect plusRect = DotIcon.plusColor.createRect();

    @Setter
    private Palette palette;
    @Setter
    private int index;
    private final Paint basePaint = new Paint();

    public ColorView(Context context) {
        super(context);
        this.palette = null;
        index = 0;
    }

    public void setColor(int color) {
        basePaint.setColor(color);
    }

    @Override
    public void onDraw(Canvas canvas) {
        final Rect dstRect = canvas.getClipBounds();

        if (index < 0) {
            canvas.drawBitmap(DotIcon.getBitmap(), plusRect, dstRect, paint);
            return;
        }

        basePaint.setColor(palette.getColor(index));
        if (palette.getIndex() == index) {
            final int dotSize = ScreenSize.getDotSize() * 3;

            canvas.drawRect(
                    new Rect(
                            dstRect.left + dotSize,
                            dstRect.top + dotSize,
                            dstRect.right - dotSize,
                            dstRect.bottom - dotSize),
                    basePaint);
            canvas.drawBitmap(DotIcon.getBitmap(), selectedRect, dstRect, paint);
        } else {
            final int dotSize = ScreenSize.getDotSize() * 4;
            canvas.drawRect(
                    new Rect(
                            dstRect.left + dotSize,
                            dstRect.top + dotSize,
                            dstRect.right - dotSize,
                            dstRect.bottom - dotSize),
                    basePaint);
            canvas.drawBitmap(DotIcon.getBitmap(), normalRect, dstRect, paint);
        }
    }
}
