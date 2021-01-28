package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;
import android.graphics.Paint;

import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.UnRedoAction;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;

public class UnRedoView extends ActionView {
    private static Paint backPaint;

    static {
        backPaint = new Paint();
        backPaint.setColor(0xff000000);
    }

    private UnRedoView(Context context) {
        super(context);
    }

    public UnRedoView(Context context, int delta) {
        super(
                context,
                delta < 0 ? DotIcon.undo.createRect() : DotIcon.redo.createRect());
        onClickAction = new UnRedoAction(delta);
    }
}
