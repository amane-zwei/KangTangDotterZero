package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.servebbs.amazarashi.kangtangdotterzero.MainActivity;
import com.servebbs.amazarashi.kangtangdotterzero.domains.GlobalContext;
import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.ChangeToolAction;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.domains.tools.Tool;

public class SelectToolView extends ActionView {
    private static Paint backPaint;

    static {
        backPaint = new Paint();
        backPaint.setColor(0xff000000);
    }

    private SelectToolView(Context context) {
        super(context);
    }

    public SelectToolView(Context context, Tool tool) {
        super(context);
        drawer = new IconDrawer(tool, tool.createIconRect());
        onClickAction = new ChangeToolAction(tool);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((MainActivity) getContext()).getToolViews().add(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ((MainActivity) getContext()).getToolViews().remove(this);
    }

    private static class IconDrawer implements Drawer {
        Tool tool;
        Rect rect;
        Rect subRect;

        protected IconDrawer(Tool tool, Rect rect) {
            this.tool = tool;
            this.rect = rect;
            this.subRect = new Rect(
                    rect.left,
                    rect.top + rect.height(),
                    rect.right,
                    rect.bottom + rect.height());
        }

        @Override
        public void draw(Context context, Canvas canvas) {
            final Rect dstRect = canvas.getClipBounds();
            if (GlobalContext.get(context).getTool().getClass() == tool.getClass()) {
                canvas.drawRect(dstRect, backPaint);
                canvas.drawBitmap(DotIcon.getBitmap(), subRect, dstRect, paint);
            } else {
                canvas.drawBitmap(DotIcon.getBitmap(), rect, dstRect, paint);
            }
        }
    }
}
