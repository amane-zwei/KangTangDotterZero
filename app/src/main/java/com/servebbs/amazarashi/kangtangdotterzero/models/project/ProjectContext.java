package com.servebbs.amazarashi.kangtangdotterzero.models.project;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.servebbs.amazarashi.kangtangdotterzero.models.GlobalContext;
import com.servebbs.amazarashi.kangtangdotterzero.models.ScreenNormalizer;
import com.servebbs.amazarashi.kangtangdotterzero.models.tools.Tool;

import lombok.Getter;

public class ProjectContext {

    @Getter
    private Project project = new Project();
    private GlobalContext globalContext;

    public void draw(Canvas dstCanvas, int alpha){
        Paint paint = new Paint();

        ScreenNormalizer screenNormalizer = globalContext.getNormalizer();

        Rect dstRect = new Rect(
                screenNormalizer.getScreenX(0),
                screenNormalizer.getScreenY(0),
                screenNormalizer.getScreenX( project.getWidth() ),
                screenNormalizer.getScreenY( project.getHeight() )
        );

        dstCanvas.drawBitmap(project.renderBitmap(), project.getRect(), dstRect, paint);
    }

    public Tool getTool() { return globalContext.getTool(); }
    public Layer getLayer() { return project.getFrame().getLayer(); }
    public ScreenNormalizer getScreenNormalizer() { return globalContext.getNormalizer(); }

    public boolean onTouch(int x, int y, int action) {
        return true;
    }

    public void setGlobalContext(GlobalContext globalContext){
        this.globalContext = globalContext;
    }
}
