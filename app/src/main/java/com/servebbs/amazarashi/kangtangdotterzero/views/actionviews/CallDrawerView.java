package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.CallDrawerAction;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;

public class CallDrawerView extends ActionView {
    public CallDrawerView(Context context) {
        super(context, DotIcon.menu.createRect());
        onClickAction = new CallDrawerAction();
    }
}
