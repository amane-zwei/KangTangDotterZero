package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.CallSaveProjectDialogAction;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;

public class CallSaveProjectDialogView extends ActionView {
    public CallSaveProjectDialogView(Context context) {
        super(context, DotIcon.save.createRect());
        onClickAction = new CallSaveProjectDialogAction();
    }
}
