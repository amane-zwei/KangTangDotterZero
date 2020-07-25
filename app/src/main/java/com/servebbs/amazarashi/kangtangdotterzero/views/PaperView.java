package com.servebbs.amazarashi.kangtangdotterzero.views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.servebbs.amazarashi.kangtangdotterzero.models.project.ProjectContext;

public class PaperView extends View {

    ProjectContext projectContext;

    public PaperView(Context context, ProjectContext projectContext){
        super(context);

        this.projectContext = projectContext;

        setBackgroundColor(0xffa0a0ff);

        setOnTouchListener(new TouchListener());
    }

    @Override
    public void onDraw(Canvas canvas){
        projectContext.draw(canvas, 0xffffffff);
    }

    public class TouchListener implements OnTouchListener{
        @Override
        public boolean onTouch(View view, MotionEvent event){
            if( projectContext.getTool().touch(event, projectContext) ){
                view.invalidate();
                return true;
            }
            return false;
        }
    }

}
