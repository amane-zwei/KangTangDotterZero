package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.Action;
import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.UnRedoAction;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.drawables.DotIconDrawable;

public class UnRedoView extends ActionView {

    public UnRedoView(Context context) {
        super(context);
        Action action  = new UnRedoAction();
        setBackground(new DotIconDrawable());
        setOnClickAction(action);
    }

    public UnRedoView applyDelta(int delta) {
        ((DotIconDrawable) getBackground()).setSrcRect(delta < 0 ? DotIcon.undo : DotIcon.redo);
        ((UnRedoAction) getOnClickAction()).setDelta(delta);
        return this;
    }
}
