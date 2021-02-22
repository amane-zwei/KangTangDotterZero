package com.servebbs.amazarashi.kangtangdotterzero.domains.tools;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.servebbs.amazarashi.kangtangdotterzero.domains.bitmap.IndexedBitmap;
import com.servebbs.amazarashi.kangtangdotterzero.domains.histories.History;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotColor;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotIcon;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Layer;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Palette;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Project;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Bucket extends Tool {
    @Override
    public DotIcon.DotIconData getIcon() { return DotIcon.bucket; }

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
                Layer layer = project.getLayer();
                DotColor color = project.getColor();
                if (paintLayer(layer, x, y, color)) {
                    project.addHistory(new BucketHistory(layer, color, x, y));
                }
                return true;
        }
        return false;
    }

    public static boolean paintLayer(Layer layer, int x, int y, DotColor color) {
        Stack<ProcessPoint> stack = new Stack<>();

        final int width = layer.getWidth();
        final int height = layer.getHeight();
        final int length = width * height;

        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }

        int paintColor = color.intValue();
        int indexedColor = color.saveIndex();

        Bitmap displayBitmap = layer.getDisplay();
        final int[] buff = new int[length];
        displayBitmap.getPixels(buff, 0, width, 0, 0, width, height);

        IndexedBitmap indexedBitmap = null;
        int[] indexedBuff = null;
        if (layer.isIndexedColor()) {
            indexedBitmap = layer.getIndexed();
            indexedBuff = new int[length];
            indexedBitmap.getBitmap().getPixels(indexedBuff, 0, width, 0, 0, width, height);
        }

        int index = x + y * width;
        final int targetColor = buff[index];
        if (targetColor == paintColor) {
            return false;
        }

        int leftEdge = x;
        int topEdge = y;
        int bottomEdge = y;
        int rightEdge = x;

        stack.push(new ProcessPoint(findRight(buff, width, index, targetColor), DIRECTION_NONE));

        while (stack.size() > 0) {
            ProcessPoint processPoint = stack.pop();
            int rightIndex = processPoint.getIndex();
            int leftIndex = paintLeft(buff, width, rightIndex, targetColor, paintColor);
            if (layer.isIndexedColor()) {
                paintLine(indexedBuff, leftIndex, rightIndex, indexedColor);
            }
            int right = rightIndex % width;
            int left = leftIndex % width;
            if (left < leftEdge) {
                leftEdge = left;
            }
            if (right > rightEdge) {
                rightEdge = right;
            }

            int tmpY = leftIndex / width;
            if (processPoint.direction != DIRECTION_DOWN) {
                if (tmpY < topEdge) {
                    topEdge = tmpY;
                }
                pushPoint(stack, buff, width, leftIndex - width, rightIndex - width, targetColor, DIRECTION_UP);
            }
            if (processPoint.direction != DIRECTION_UP) {
                if (tmpY > bottomEdge) {
                    bottomEdge = tmpY;
                }
                pushPoint(stack, buff, width, leftIndex + width, rightIndex + width, targetColor, DIRECTION_DOWN);
            }
        }

        int start = leftEdge + topEdge * width;
        int dstWidth = rightEdge - leftEdge + 1;
        int dstHeight = bottomEdge - topEdge + 1;
        displayBitmap.setPixels(buff, start, width, leftEdge, topEdge, dstWidth, dstHeight);

        if (layer.isIndexedColor()) {
            indexedBitmap.getBitmap().setPixels(indexedBuff, start, width, leftEdge, topEdge, dstWidth, dstHeight);
        }
        return true;
    }

    private static int findRight(int[] buff, int width, int index, int targetColor) {
        int rightEdge = calcRightIndex(index, width);
        for (index++; index < rightEdge; index++) {
            if (targetColor != buff[index]) {
                return index - 1;
            }
        }
        return rightEdge - 1;
    }

    private static void pushPoint(Stack<ProcessPoint> stack, int[] buff, int width, int start, int end, int targetColor, int direction) {
        if (start < 0 || start >= buff.length) {
            return;
        }
        for (start++; start <= end; start++) {
            if (targetColor != buff[start] && targetColor == buff[start - 1]) {
                stack.push(new ProcessPoint(start - 1, direction));
            }
        }
        if (targetColor != buff[start - 1]) {
            return;
        }
        int right = calcRightIndex(start, width);
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

    private static int paintLeft(int[] buff, int width, int index, int targetColor, int paintColor) {
        int leftEdge = calcLeftIndex(index, width);
        buff[index] = paintColor;
        for (index--; index >= leftEdge; index--) {
            if (targetColor != buff[index]) {
                return index + 1;
            }
            buff[index] = paintColor;
        }
        return leftEdge;
    }

    private static void paintLine(int[] buff, int start, int end, int paintColor) {
        for (; start <= end; start++) {
            buff[start] = paintColor;
        }
    }

    private static int calcLeftIndex(int index, int width) {
        return index - index % width;
    }

    private static int calcRightIndex(int index, int width) {
        return index - index % width + width;
    }

    @NoArgsConstructor
    public static class BucketHistory extends History {
        @Getter
        private DotColor color;
        @Getter
        private int x;
        @Getter
        private int y;

        @JsonIgnore
        private final Paint paint = new Paint();

        public BucketHistory(Layer layer, DotColor color, int x, int y) {
            super(layer);
            this.color = color;
            this.x = x;
            this.y = y;
        }

        @Override
        public void draw(Layer layer, Palette palette) {
            paintLayer(layer, x, y, color.applyPalette(palette));
        }
    }

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
