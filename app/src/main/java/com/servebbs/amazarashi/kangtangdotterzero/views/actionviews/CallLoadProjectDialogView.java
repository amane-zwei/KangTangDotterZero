package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.models.actions.CallLoadProjectDialogAction;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;

public class CallLoadProjectDialogView extends ActionView {
    public CallLoadProjectDialogView(Context context) {
        super(context, DotIcon.load.createRect());
        onClickAction = new CallLoadProjectDialogAction();
    }
}
