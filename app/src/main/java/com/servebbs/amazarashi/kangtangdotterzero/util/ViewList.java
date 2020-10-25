package com.servebbs.amazarashi.kangtangdotterzero.util;

import android.view.View;

import java.util.ArrayList;

public class ViewList extends ArrayList<View> {
    public void invalidateViews() {
        for (View view : this) {
            view.invalidate();
        }
    }
}
