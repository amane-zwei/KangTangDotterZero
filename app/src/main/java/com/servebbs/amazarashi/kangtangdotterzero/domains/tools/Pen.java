package com.servebbs.amazarashi.kangtangdotterzero.domains.tools;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.MotionEvent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.servebbs.amazarashi.kangtangdotterzero.domains.histories.History;
import com.servebbs.amazarashi.kangtangdotterzero.domains.lowlevel.Point;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotColor;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Layer;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Palette;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class Pen extends Tool {
    private static final PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC);

    private ArrayList<Point> buff = null;
    private Layer layer;
    private DotColor color;
    private boolean isDraw;

    @Override
    public DotIcon.DotIconData getIcon() { return DotIcon.pen; }

    @Override
    public boolean touch(Event event) {
        Project project = event.getProject();
        int x = event.getX();
        int y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                layer = project.getLayer();
                color = getColor(project);
                onDown(project, x, y);
                return true;
            case MotionEvent.ACTION_MOVE:
                onMove(project, x, y);
                return true;
            case MotionEvent.ACTION_UP:
                onUp(project);
                return true;
        }
        return false;
    }

    @Override
    public void clear() {
        buff = null;
        layer = null;
        color = null;
        isDraw = false;
    }

    @Override
    public void flush(Event event) {
        onUp(event.getProject());
    }

    protected DotColor getColor(Project project) {
        return project.getColor();
    }

    public void onDown(Project project, int x, int y) {
        this.buff = new ArrayList<>();
        buff.add(new Point(x, y));
        innerDraw(layer.getDisplayCanvas(), buff, createPaint(color.intValue()));
        if (layer.isIndexedColor()) {
            innerDraw(layer.getIndexedCanvas(), buff, createPaint(color.saveIndex()));
        }
        isDraw = !project.isOut(x, y);
    }

    public boolean onMove(Project project, int x, int y) {
        if (buff == null || buff.size() < 1) {
            onDown(project, x, y);
            return true;
        }
        Point prev = buff.get(buff.size() - 1);
        if (x != prev.x || y != prev.y) {
            buff.add(new Point(x, y));
            innerDraw(layer.getDisplayCanvas(), buff, createPaint(color.intValue()));
            if (layer.isIndexedColor()) {
                innerDraw(layer.getIndexedCanvas(), buff, createPaint(color.saveIndex()));
            }
            isDraw |= !project.isOut(x, y);
        }
        return true;
    }

    public boolean onUp(Project project) {
        if (isDraw) {
            project.addHistory(new PenHistory(layer, buff, color));
        }
        clear();
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

    @NoArgsConstructor
    @JsonTypeName("pen")
    public static class PenHistory extends History {
        @Getter
        private DotColor color;
        @Getter
        @JsonProperty("points")
        private List<Point> buff;
        @JsonIgnore
        private Paint paint;
        @JsonIgnore
        private Paint indexedPaint;

        public PenHistory(Layer layer, List<Point> buff, DotColor color) {
            super(layer);
            this.buff = buff;
            this.color = color;
            set();
        }

        public void set() {
            if (color.isIndexedColor()) {
                paint = createPaint(color.intValue());
                indexedPaint = createPaint(color.saveIndex());
            } else {
                paint = createPaint(color.intValue());
                indexedPaint = null;
            }
        }

        public void draw(Layer layer, Palette palette) {
            if (layer.isIndexedColor()) {
                paint.setColor(palette.getColor(color.plainIndex()).getValue());
                innerDraw(layer.getDisplayCanvas(), buff, paint);
                innerDraw(layer.getIndexedCanvas(), buff, indexedPaint);
            } else {
                innerDraw(layer.getDisplayCanvas(), buff, paint);
            }
        }
    }
}
