package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.Action;
import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.FlipCursorModeAction;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.drawables.BooleanDotIconDrawable;
import com.servebbs.amazarashi.kangtangdotterzero.fragments.MainFragment;

public class FlipCursorModeView extends ActionView {
    public FlipCursorModeView(Context context) {
        super(context);
        Action action = new FlipCursorModeAction();
        DotIcon.DotIconData dotIconData = action.getIcon();
        setBackground(new BooleanDotIconDrawable(
                dotIconData.createRect(0, dotIconData.getHeight()),
                dotIconData.createRect(),
                        () -> MainFragment.get(context).getMainView().isCursorMode()));
        setOnClickAction(action);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        MainFragment.get(getContext()).getMainView().getCursorModeViews().add(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MainFragment.get(getContext()).getMainView().getCursorModeViews().remove(this);
    }
}
