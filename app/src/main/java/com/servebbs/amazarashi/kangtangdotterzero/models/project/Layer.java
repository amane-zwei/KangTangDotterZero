package com.servebbs.amazarashi.kangtangdotterzero.models.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.servebbs.amazarashi.kangtangdotterzero.models.bitmap.ColorList;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class Layer extends LayerData {
    private final int intervalOfHistory = 4;

    @Getter
    private int id;

    @JsonIgnore
    @Getter
    private Canvas displayCanvas;
    @JsonIgnore
    @Getter
    private Canvas indexedCanvas;

    @JsonIgnore
    @Getter
    private int written;

    @JsonIgnore
    @Getter
    private LayerHistory history;

    static Paint copyPaint;

    static {
        copyPaint = new Paint();
    }

    public Layer(int id, int width, int height, boolean isIndexedColor) {
        this.id = id;
        if (isIndexedColor) {
            useIndexedColor();
        }
        resize(width, height);
        written = 0;
        history = new LayerHistory(this);
    }

    public Layer restore(ColorList colorList, Bitmap src, boolean isIndexedColor) {
        super.restore(colorList, src, isIndexedColor);
        displayCanvas = new Canvas(display);
        if (isIndexedColor()) {
            indexedCanvas = new Canvas(indexed.getBitmap());
        }
        history = new LayerHistory(this);
        return this;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        displayCanvas = new Canvas(display);
        if (isIndexedColor()) {
            indexedCanvas = new Canvas(indexed.getBitmap());
        }
    }

    public Canvas render(Canvas destination) {
        destination.drawBitmap(display, 0, 0, copyPaint);
        return destination;
    }

    public void write(int historyIndex) {
        this.historyIndex = historyIndex;
        if (written < intervalOfHistory - 1) {
            written++;
        } else {
            history.add(this);
            written = 0;
        }
    }

    public void touch() {
        if (written < intervalOfHistory - 1) {
            written++;
        } else {
            history.addIndex();
            written = 0;
        }
    }

    public int restoreLayerHistory(int index) {
        restore(history.applyIndex(index));
        displayCanvas = new Canvas(display);
        if (isIndexedColor()) {
            indexedCanvas = new Canvas(indexed.getBitmap());
        }
        written = 0;
        return historyIndex;
    }

//    public boolean setIndexedBitmap(Bitmap src, ColorList colorList) {
//        if (src == null)
//            return false;
//
//        int width = src.getWidth();
//        int height = src.getHeight();
//        try {
//            this.display = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//            this.indexed = IndexedBitmap.createIndexedBitmap(width, height);
//        } catch (java.lang.Exception e) {
//            return false;
//        }
//        this.indexed.fromBitmap(src, colorList);
//        return true;
//    }
}