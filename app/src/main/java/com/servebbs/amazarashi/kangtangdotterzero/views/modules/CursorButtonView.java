package com.servebbs.amazarashi.kangtangdotterzero.views.modules;

import android.content.Context;
import android.view.MotionEvent;

import com.servebbs.amazarashi.kangtangdotterzero.fragments.MainFragment;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;

public class CursorButtonView extends SimpleIconView{
    public CursorButtonView(Context context) {
        super(context);

        setClickable(false);
        setRect(DotIcon.cursorButton.createRect());
   }

   @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                MainFragment.get(getContext()).getMainView().keyDown();
                return true;
            case MotionEvent.ACTION_UP:
                MainFragment.get(getContext()).getMainView().keyUp();
                return true;
        }
        return false;
   }
}
