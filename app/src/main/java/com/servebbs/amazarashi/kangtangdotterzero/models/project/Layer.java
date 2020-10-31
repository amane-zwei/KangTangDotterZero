package com.servebbs.amazarashi.kangtangdotterzero.models.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.servebbs.amazarashi.kangtangdotterzero.models.bitmap.ColorList;
import com.servebbs.amazarashi.kangtangdotterzero.models.bitmap.IndexedBitmap;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(of = "id", callSuper = false)
public class Layer extends LayerData{
    private final int intervalOfHistory = 4;

    @Getter
    private final int id;

    @JsonIgnore
    @Getter
    private int written;

    @JsonIgnore
    @Getter
    private final LayerHistory history;

    static Paint copyPaint;
    static {
        copyPaint = new Paint();
    }

    @JsonIgnore
    public Canvas createCanvas() {
        return new Canvas(display);
    }

    public void useIndexed(ColorList colorList) {
        indexed = IndexedBitmap.createIndexedBitmap(display.getWidth(), display.getHeight());
        indexed.fromBitmap(display, colorList);
    }

    public void stopIndexed(ColorList colorList) {
        if (indexed != null) {
            display = indexed.translateColor(display, colorList);
            indexed = null;
        }
    }

    private Layer(int id) {
        this.id = id;
        written = 0;
        history = null;
    }

    public Layer(int id, int width, int height) {
        this.id = id;
        changeSize(width, height);
        written = 0;
        history = new LayerHistory(this);
    }

    public Layer(int id, Layer src) {
        this.id = id;
        display = Bitmap.createBitmap(src.display);
        indexed = src.indexed == null ? null : IndexedBitmap.createIndexedBitmap(src.indexed);
        written = 0;
        history = new LayerHistory(this);
    }

    public void resetToFixed(int index) {
        /*
        LayerHistory history = null;
        for( int idx=histories.size()-1; idx>=0; idx--){
            LayerHistory tmp = histories.get(idx);
            if( tmp.index < index ) {
                history = tmp;
                break;
            }
        }

        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));

        Bitmap fixed = history.bitmap;

        new Canvas(display).drawBitmap(fixed, 0, 0, paint);
        */
    }

    public void clear() {
        new Canvas(display).drawColor(0x00000000, PorterDuff.Mode.SRC);
        if (indexed != null) {
            indexed.clear();
        }
        history.clear(this);
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
        written = 0;
        return historyIndex;
    }

    public boolean applyBitmap(Bitmap src) {
        if (src == null)
            return false;

        try {
            this.display = src.copy(Bitmap.Config.ARGB_8888, true);
            this.indexed = null;
        } catch (java.lang.Exception e) {
            return false;
        }
        return true;
    }

    public boolean setIndexedBitmap(Bitmap src, ColorList colorList) {
        if (src == null)
            return false;

        int width = src.getWidth();
        int height = src.getHeight();
        try {
            this.display = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            this.indexed = IndexedBitmap.createIndexedBitmap(width, height);
        } catch (java.lang.Exception e) {
            return false;
        }
        this.indexed.fromBitmap(src, colorList);
        return true;
    }

    public static Layer create(int id, Bitmap bitmap) {
        Layer layer = new Layer(id);

        if (layer.applyBitmap(bitmap))
            return layer;
        else
            return null;
    }

    public static Layer create(int id, int width, int height) {
        Layer layer = new Layer(id);
        if (layer.changeSize(width, height))
            return layer;
        else
            return null;
    }

    public Layer createLayer(int id) {
        Layer layer = new Layer(id);
        if (layer.changeSize(getWidth(), getHeight()))
            return layer;
        else
            return null;
    }

    public boolean resize(int w, int h) {
//        Bitmap fixed;
//        Bitmap display;
//        Bitmap indexed = null;
//
//        if( w > 65535 || h > 65535 )
//            return false;
//
//        try{
//            fixed   = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//            display = fixed.copy(fixed.getConfig(), true);
//            if( this.indexed!=null ){
//                indexed.resize(w, h);
//            }
//        } catch( java.lang.Exception e){
//            return false;
//        }
//
//        new Canvas(fixed)  .drawBitmap(this.display, 0, 0, null);
//        new Canvas(display).drawBitmap(this.display, 0, 0, null);
//        this.fixed   = fixed;
//        this.display = display;
//        if( this.iFixed!=null ){
//            new Canvas(iFixed) .drawBitmap(this.iFixed,  0, 0, null);
//            this.iFixed  = iFixed;
//        }
        return true;
    }

    public boolean changeSize(int width, int height) {
        if (width > 65535 || height > 65535)
            return false;

        try {
            //           fixed   = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            //           display = fixed.copy(fixed.getConfig(), true);
            display = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        } catch (java.lang.Exception e) {
            return false;
        }

        clear();

        return true;
    }
}