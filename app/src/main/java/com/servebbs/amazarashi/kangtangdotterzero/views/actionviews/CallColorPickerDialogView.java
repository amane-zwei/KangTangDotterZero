package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;

import com.servebbs.amazarashi.kangtangdotterzero.models.actions.CallColorPickerDialogAction;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;

public class CallColorPickerDialogView extends ActionView {
    public CallColorPickerDialogView(Context context) {
        super(context);
        onClickAction = new CallColorPickerDialogAction();
    }

    @Override
    protected RectSupplier supplyRect() {
        return DotIcon.colorPicker::createRect;
    }
}
