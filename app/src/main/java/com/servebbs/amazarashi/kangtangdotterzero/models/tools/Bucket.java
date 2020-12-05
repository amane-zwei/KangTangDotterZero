package com.servebbs.amazarashi.kangtangdotterzero.models.tools;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotColor;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Layer;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Project;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Bucket extends Tool {
    @Override
    public Rect createIconRect() {
        return DotIcon.bucket.createRect();
    }

    @Override
    public boolean touch(Event event) {
        Project project = event.getProject();
        int x = event.getX();
        int y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
                paint(project, x, y, new DotColor(project.getPalette().getColor()));
                return true;
        }
        return false;
    }

    public Integer paint(Project project, int x, int y, DotColor color) {
        Stack<ProcessPoint> stack = new Stack<>();

        Layer layer = project.getLayer();
        final int width = project.getWidth();
        final int height = project.getHeight();
        final int length = width * height;

        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null;
        }

        Bitmap srcBitmap = layer.getDisplay();
        final int[] buff = new int[length];
        srcBitmap.getPixels(buff, 0, width, 0, 0, width, height);

        int index = x + y * width;
        final int targetColor = buff[index];
        if (targetColor == color.intValue()) {
            return null;
        }

        stack.push(new ProcessPoint(findRight(buff, width, index, targetColor), DIRECTION_NONE));

        while (stack.size() > 0) {
            ProcessPoint processPoint = stack.pop();
            int left = paintLeft(buff, width, processPoint.getIndex(), targetColor, color.intValue());
            if (processPoint.direction != DIRECTION_DOWN) {
                pushPoint(stack, buff, width, left - width, processPoint.getIndex() - width, targetColor, DIRECTION_UP);
            }
            if (processPoint.direction != DIRECTION_UP) {
                pushPoint(stack, buff, width, left + width, processPoint.getIndex() + width, targetColor, DIRECTION_DOWN);
            }
        }

        srcBitmap.setPixels(buff, 0, width, 0, 0, width, height);

        return null;
    }

    private int findRight(int[] buff, int width, int index, int targetColor) {
        int rightEdge = calcRight(index, width);
        for (index++; index < rightEdge; index++) {
            if (targetColor != buff[index]) {
                return index - 1;
            }
        }
        return rightEdge - 1;
    }

    private void pushPoint(Stack<ProcessPoint> stack, int[] buff, int width, int start, int end, int targetColor, int direction) {
        if (start < 0 || start >= buff.length) {
            return;
        }
        int right = calcRight(start, width);
        for (start++; start <= end; start++) {
            if (targetColor != buff[start] && targetColor == buff[start - 1]) {
                stack.push(new ProcessPoint(start - 1, direction));
            }
        }
        if (targetColor != buff[start - 1]) {
            return;
        }
        for (; start < right; start++) {
            if (targetColor != buff[start]) {
                stack.push(new ProcessPoint(start - 1, DIRECTION_NONE));
                break;
            }
        }
        if(start == right) {
            stack.push(new ProcessPoint(right - 1, DIRECTION_NONE));
        }
    }

    private int paintLeft(int[] buff, int width, int index, int targetColor, int paintColor) {
        int leftEdge = calcLeft(index, width);
        buff[index] = paintColor;
        for (index--; index >= leftEdge; index--) {
            if (targetColor != buff[index]) {
                return index + 1;
            }
            buff[index] = paintColor;
        }
        return leftEdge;
    }

    private int calcLeft(int index, int width) {
        return index - index % width;
    }

    private int calcRight(int index, int width) {
        return index - index % width + width;
    }

//    public static class BucketHistory extends History {
//        private final int color;
//        private final List<Point> buff;
//        @JsonIgnore
//        private final Paint paint;
//        @JsonIgnore
//        private final Paint indexedPaint;
//
//        public BucketHistory(Layer layer, List<Point> buff, int color) {
//            super(layer);
//            this.buff = buff;
//            this.color = color;
//            paint = createPaint(color);
//            indexedPaint = layer.isIndexedColor() ? createPaint(color) : null;
//        }
//
//        public void draw(Layer layer, Palette palette) {
//            if (layer.isIndexedColor()) {
//                indexedPaint.setColor(palette.getColor(IndexedBitmap.toPlainIndex(color)));
//                innerDraw(layer.getDisplayCanvas(), buff, indexedPaint);
//                innerDraw(layer.getIndexedCanvas(), buff, paint);
//            } else {
//                innerDraw(layer.getDisplayCanvas(), buff, paint);
//            }
//        }
//    }

    private static final int DIRECTION_NONE = 0;
    private static final int DIRECTION_UP = 1;
    private static final int DIRECTION_DOWN = 2;
    @AllArgsConstructor
    @Getter
    public static class ProcessPoint {
        public final int index;
        public final int direction;
    }

    public static class Stack<E> extends ArrayList<E> {
        public void push(E element) {
            add(element);
        }

        public E pop() {
            return remove(size() - 1);
        }
    }
}
