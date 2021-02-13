package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.Action;
import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.CallColorPickerDialogAction;
import com.servebbs.amazarashi.kangtangdotterzero.drawables.DotIconDrawable;

public class CallColorPickerDialogView extends ActionView {
    public CallColorPickerDialogView(Context context) {
        super(context);
        Action action  = new CallColorPickerDialogAction();
        setBackground(new DotIconDrawable(action.getIcon()));
        setOnClickAction(action);
    }
}
