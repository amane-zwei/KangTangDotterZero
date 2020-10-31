package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.servebbs.amazarashi.kangtangdotterzero.models.actions.UnRedoAction;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.ProjectContext;
import com.servebbs.amazarashi.kangtangdotterzero.models.tools.Tool;

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
