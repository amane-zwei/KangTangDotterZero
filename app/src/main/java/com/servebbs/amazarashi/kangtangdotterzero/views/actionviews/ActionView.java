package com.servebbs.amazarashi.kangtangdotterzero.views.actionviews;

import android.content.Context;
import android.view.View;

import com.servebbs.amazarashi.kangtangdotterzero.domains.actions.Action;

import lombok.Getter;
import lombok.Setter;

public abstract class ActionView extends View {

    @Getter
    @Setter
    private Action onClickAction;

    public ActionView(Context context) {
        super(context);
    }
}
