package com.servebbs.amazarashi.kangtangdotterzero.views;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.servebbs.amazarashi.kangtangdotterzero.KTDZApplication;
import com.servebbs.amazarashi.kangtangdotterzero.views.menu.HorizontalMenuView;

public class MainView extends FrameLayout {

    public MainView(Context context) {
        super(context);

        addPaperView(context);
    }

    private void addPaperView(Context context){
        PaperView paperView = new PaperView(context, ((KTDZApplication)context.getApplicationContext()).getProjectContext());
        paperView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        );
        this.addView(paperView);

        HorizontalMenuView menuView = new HorizontalMenuView(context);
        menuView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM)
        );
        this.addView(menuView);
    }
}
