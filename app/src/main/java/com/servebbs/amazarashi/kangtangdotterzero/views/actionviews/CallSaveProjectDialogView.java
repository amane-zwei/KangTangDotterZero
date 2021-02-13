package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.Action;
import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.CallSaveProjectDialogAction;
import com.servebbs.amazarashi.kangtangdotterzero.drawables.DotIconDrawable;

public class CallSaveProjectDialogView extends ActionView {
    public CallSaveProjectDialogView(Context context) {
        super(context);
        Action action = new CallSaveProjectDialogAction();
        setBackground(new DotIconDrawable(action.getIcon()));
        setOnClickAction(action);
    }
}
