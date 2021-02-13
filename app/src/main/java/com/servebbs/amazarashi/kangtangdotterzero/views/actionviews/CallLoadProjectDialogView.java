package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.Action;
import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.CallLoadProjectDialogAction;
import com.servebbs.amazarashi.kangtangdotterzero.drawables.DotIconDrawable;

public class CallLoadProjectDialogView extends ActionView {
    public CallLoadProjectDialogView(Context context) {
        super(context);
        Action action = new CallLoadProjectDialogAction();
        setBackground(new DotIconDrawable(action.getIcon()));
        setOnClickAction(action);
    }
}
