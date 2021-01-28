package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.Action;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;

import lombok.Getter;

public abstract class ActionView extends View {
    protected final static Paint paint = new Paint();

    @Getter
    protected Action onClickAction;

    protected Drawer drawer;

    public ActionView(Context context) {
        super(context);
    }

    public ActionView(Context context, Rect rect) {
        super(context);
        drawer = new IconDrawer(rect);
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawer.draw(getContext(), canvas);
    }

    protected interface Drawer {
        void draw(Context context, Canvas canvas);
    }

    protected static class IconDrawer implements Drawer {
        Rect rect;

        protected IconDrawer(Rect rect) {
            this.rect = rect;
        }

        @Override
        public void draw(Context context, Canvas canvas) {
            final Rect dstRect = canvas.getClipBounds();
            canvas.drawBitmap(DotIcon.getBitmap(), rect, dstRect, paint);
        }
    }
}
