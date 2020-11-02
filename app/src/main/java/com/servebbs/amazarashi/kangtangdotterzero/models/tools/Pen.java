package com.servebbs.amazarashi.kangtangdotterzero.models.tools;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.servebbs.amazarashi.kangtangdotterzero.models.bitmap.IndexedBitmap;
import com.servebbs.amazarashi.kangtangdotterzero.models.histories.History;
import com.servebbs.amazarashi.kangtangdotterzero.models.lowlevel.Point;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Layer;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Palette;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.ProjectContext;

import java.util.ArrayList;
import java.util.List;

public class Pen extends Tool {
    private static final PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC);

    private ArrayList<Point> buff = null;
    private Layer layer;
    private int color;
    private int colorIndex;
    private boolean isDraw;

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
                if (layer.isIndexedColor()) {
                    colorIndex = getColorIndex(projectContext);
                }
                onDown(projectContext.getProject(), x, y);
                return true;
            case MotionEvent.ACTION_MOVE:
                onMove(projectContext.getProject(), x, y);
                return true;
            case MotionEvent.ACTION_UP:
                onUp(projectContext.getProject());
                return true;
        }
        return false;
    }

    public int getColor(ProjectContext context) {
        return context.getProject().getPalette().getColor();
    }

    public int getColorIndex(ProjectContext context) {
        return IndexedBitmap.toSaveIndex(context.getProject().getPalette().getIndex());
    }

    public void onDown(Project project, int x, int y) {
        this.buff = new ArrayList<>();
        buff.add(new Point(x, y));
        innerDraw(layer.getDisplayCanvas(), buff, createPaint(color));
        if (layer.isIndexedColor()) {
            innerDraw(layer.getIndexedCanvas(), buff, createPaint(colorIndex));
        }
        isDraw = !project.isOut(x, y);
    }

    public boolean onMove(Project project, int x, int y) {
        Point prev = buff.get(buff.size() - 1);
        if (x != prev.x || y != prev.y) {
            buff.add(new Point(x, y));
            innerDraw(layer.getDisplayCanvas(), buff, createPaint(color));
            if (layer.isIndexedColor()) {
                innerDraw(layer.getIndexedCanvas(), buff, createPaint(colorIndex));
            }
            isDraw |= !project.isOut(x, y);
        }
        return true;
    }

    public boolean onUp(Project project) {
        if (isDraw) {
            if (project.isIndexedColor()) {
                project.addHistory(new PenHistory(layer, buff, colorIndex));
            } else {
                project.addHistory(new PenHistory(layer, buff, color));
            }
        }
        layer = null;
        buff = null;
        return true;
    }

    private static Paint createPaint(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(1);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setXfermode(porterDuffXfermode);
        return paint;
    }

    private static void innerDraw(Canvas canvas, List<Point> buff, Paint paint) {
        int size = buff.size();
        Point prev = buff.get(0);

//        if( width > 1.5  ){
//            paint.setStrokeCap(Paint.Cap.ROUND);
//            canvas.drawCircle(prev.x, prev.y, width/2, paint);
//        } else {
        canvas.drawPoint(prev.x, prev.y, paint);
//        }

        if (size == 1) {
            canvas.drawLine(prev.x, prev.y, prev.x, prev.y, paint);
            return;
        }

        for (int idx = 1; idx < size; idx++) {
            Point next = buff.get(idx);
            canvas.drawLine(prev.x, prev.y, next.x, next.y, paint);
            prev = next;
        }
    }

    public static class PenHistory extends History {
        private final int color;
        private final List<Point> buff;
        @JsonIgnore
        private final Paint paint;
        @JsonIgnore
        private final Paint indexedPaint;

        public PenHistory(Layer layer, List<Point> buff, int color) {
            super(layer);
            this.buff = buff;
            this.color = color;
            paint = createPaint(color);
            indexedPaint = layer.isIndexedColor() ? createPaint(color) : null;
        }

        public void draw(Layer layer, Palette palette) {
            if (layer.isIndexedColor()) {
                indexedPaint.setColor(palette.getColor(IndexedBitmap.toPlainIndex(color)));
                innerDraw(layer.getDisplayCanvas(), buff, indexedPaint);
                innerDraw(layer.getIndexedCanvas(), buff, paint);
            } else {
                innerDraw(layer.getDisplayCanvas(), buff, paint);
            }
        }
    }
}
