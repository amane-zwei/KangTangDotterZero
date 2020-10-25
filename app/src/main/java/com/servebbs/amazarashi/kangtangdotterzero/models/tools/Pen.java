package com.servebbs.amazarashi.kangtangdotterzero.models.tools;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.servebbs.amazarashi.kangtangdotterzero.models.lowlevel.Point;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Layer;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.ProjectContext;

import java.util.ArrayList;

public class Pen extends Tool {
    private static PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC);

    private ArrayList<Point> buff = null;
    private Layer layer;
    private int color;

    @Override
    public Rect createIconRect() {
        return DotIcon.pen.createRect();
    }

    @Override
    public boolean touch(MotionEvent event, ProjectContext projectContext) {
        int x = projectContext.getScreenNormalizer().getPaperX(event.getX());
        int y = projectContext.getScreenNormalizer().getPaperY(event.getY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                layer = projectContext.getLayer();
                color = getColor(projectContext);
                onDown(x, y);
                return true;
            case MotionEvent.ACTION_MOVE:
                onMove(x, y);
                return true;
            case MotionEvent.ACTION_UP:
                onUp();
                return true;
        }
        return false;
    }

    public int getColor(ProjectContext context) {
        return context.getProject().getPalette().getColor();
    }

    public void onDown(int x, int y) {
        this.buff = new ArrayList<>();
        buff.add(new Point(x, y));
    }

    public boolean onMove(int x, int y) {
        Point prev = buff.get(buff.size() - 1);
        if (x != prev.x || y != prev.y) {
            buff.add(new Point(x, y));
            innerDraw(layer.getCanvas(), color, new Paint());
        }
        return true;
    }

    public boolean onUp() {
        innerDraw(layer.getCanvas(), color, new Paint());
        return true;
    }

    private void innerDraw(Canvas canvas, int color, Paint paint) {
        int size = buff.size();
        Point prev = buff.get(0);

        paint.setColor(color);
        paint.setStrokeWidth(1);
        paint.setXfermode(porterDuffXfermode);

//        if( width > 1.5  ){
//            paint.setStrokeCap(Paint.Cap.ROUND);
//            canvas.drawCircle(prev.x, prev.y, width/2, paint);
//        } else {
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(prev.x, prev.y, paint);
//        }
        for (int idx = 1; idx < size; idx++) {
            Point next = buff.get(idx);
            canvas.drawLine(prev.x, prev.y, next.x, next.y, paint);
            prev = next;
        }
    }

}
