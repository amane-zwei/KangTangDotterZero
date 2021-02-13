package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.Action;
import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.CallDrawerAction;
import com.servebbs.amazarashi.kangtangdotterzero.drawables.DotIconDrawable;

public class CallDrawerView extends ActionView {
    public CallDrawerView(Context context) {
        super(context);
        Action action = new CallDrawerAction();
        setBackground(new DotIconDrawable(action.getIcon()));
        setOnClickAction(action);
    }
}
