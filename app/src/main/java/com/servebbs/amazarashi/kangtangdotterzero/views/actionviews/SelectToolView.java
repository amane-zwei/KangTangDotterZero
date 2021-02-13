package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.servebbs.amazarashi.kangtangdotterzero.MainActivity;
import com.servebbs.amazarashi.kangtangdotterzero.domains.GlobalContext;
import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.Action;
import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.ChangeToolAction;
import com.servebbs.amazarashi.kangtangdotterzero.domains.tools.Tool;
import com.servebbs.amazarashi.kangtangdotterzero.drawables.DotIconDrawable;

import lombok.Setter;

public class SelectToolView extends ActionView {
    private static final Paint backPaint;

    static {
        backPaint = new Paint();
        backPaint.setColor(0xff000000);
    }

    public SelectToolView(Context context) {
        super(context);
        Action action = new ChangeToolAction();
        setBackground(new ToolIconDrawable());
        setOnClickAction(action);
    }

    public SelectToolView applyTool(Tool tool) {
        ((ChangeToolAction) getOnClickAction()).setTool(tool);
        ((ToolIconDrawable) getBackground()).setTool(tool);
        return this;
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

    private class ToolIconDrawable extends DotIconDrawable {
        @Setter
        Tool tool;

        @Override
        public void draw(@NonNull Canvas canvas) {
            final Rect dst = getDst();
            if (GlobalContext.get(getContext()).getTool().getClass() == tool.getClass()) {
                canvas.drawRect(dst, backPaint);
                setSrcRect(tool.getIcon(), 0, tool.getIcon().getHeight());
            } else {
                setSrcRect(tool.getIcon());
            }
            super.draw(canvas);
        }
    }
}
