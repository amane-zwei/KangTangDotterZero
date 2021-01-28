package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.CallColorPickerDialogAction;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;

public class CallColorPickerDialogView extends ActionView {
    public CallColorPickerDialogView(Context context) {
        super(context, DotIcon.colorPicker.createRect());
        onClickAction = new CallColorPickerDialogAction();
    }
}
