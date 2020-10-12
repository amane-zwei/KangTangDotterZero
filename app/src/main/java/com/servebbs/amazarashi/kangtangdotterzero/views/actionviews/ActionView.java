package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;
import android.graphics.Rect;

import com.servebbs.amazarashi.kangtangdotterzero.models.actions.Action;
import com.servebbs.amazarashi.kangtangdotterzero.views.modules.SimpleIconView;

import lombok.Getter;

public abstract class ActionView extends SimpleIconView {
    @Getter
    protected Action onClickAction;

    public ActionView(Context context) {
        super(context);
        setRect(supplyRect().get());
    }

    protected abstract RectSupplier supplyRect();

    protected interface RectSupplier {
        Rect get();
    }
}
