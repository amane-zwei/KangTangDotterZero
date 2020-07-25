package com.servebbs.amazarashi.kangtangdotterzero.models.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;

import com.servebbs.amazarashi.kangtangdotterzero.models.bitmap.ColorArray;
import com.servebbs.amazarashi.kangtangdotterzero.models.bitmap.IndexedBitmap;

public class Layer {
    private Bitmap display;
    private IndexedBitmap indexed = null;
    private LayerHistory history = null;

    static Paint copyPaint;
    static {
        copyPaint = new Paint();
//        copyPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }

    public int  getWidth() { return display.getWidth(); }
    public int  getHeight(){ return display.getHeight(); }

    public Bitmap getDisplay(){ return display; }
    public Bitmap getIndexed(){ return indexed.getBitmap(); }
    public Canvas getCanvas(){ return new Canvas(display); }

    public void useIndexed(ColorArray colorArray) {
        indexed = IndexedBitmap.createIndexedBitmap(display.getWidth(), display.getHeight());
        indexed.fromBitmap(display, colorArray);
    }
    public void stopIndexed(ColorArray colorArray) {
        if( indexed != null ) {
            display = indexed.translateColor(display, colorArray);
            indexed = null;
        }
    }

    private Layer(){
        display = null;
        indexed = null;
        history = new LayerHistory();
    }

    public Layer(int w, int h){
        changeSize(w, h);
        history = new LayerHistory();
    }

    public Layer(Layer src){
        display = Bitmap.createBitmap(src.display);
        indexed = src.indexed==null ? null : IndexedBitmap.createIndexedBitmap(src.indexed);
        history = new LayerHistory();
    }

    public void resetToFixed(int index){
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

    public void clear(){
        new Canvas(display).drawColor(0x00000000, PorterDuff.Mode.SRC);
        if( indexed!=null )
            indexed.clear();
//        histories.clear();
    }

    public boolean isOut(int x, int y){
        int w = getWidth();
        int h = getHeight();

        if( x < 0 || w <= x || y < 0 || h <= y)
            return true;
        return false;
    }

    public Canvas render(Canvas destination) {
        destination.drawBitmap(display, 0, 0, copyPaint);
        return destination;
    }

    public boolean setBitmap(Bitmap src){
        if( src==null )
            return false;

        try{
            this.display = src.copy(Bitmap.Config.ARGB_8888, true);
            this.indexed = null;
        } catch( java.lang.Exception e){
            return false;
        }
        return true;
    }
    public boolean setIndexedBitmap(Bitmap src, ColorArray colorArray){
        if( src==null )
            return false;

        int width  = src.getWidth();
        int height = src.getHeight();
        try{
            this.display = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            this.indexed = IndexedBitmap.createIndexedBitmap(width, height);
        } catch( java.lang.Exception e){
            return false;
        }
        this.indexed.fromBitmap(src, colorArray);
        return true;
    }

    public static Layer create(Bitmap bitmap){
        Layer paper = new Layer();

        if( paper.setBitmap(bitmap) )
            return paper;
        else
            return null;
    }
    public static Layer create(int width, int height){
        Layer layer = new Layer();
        if( layer.changeSize(width, height) )
            return layer;
        else
            return null;
    }
    public Layer createLayer(){
        Layer layer = new Layer();
        if( layer.changeSize(getWidth(), getHeight()) )
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
        if( width > 65535 || height > 65535 )
            return false;

        try{
 //           fixed   = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
 //           display = fixed.copy(fixed.getConfig(), true);
            display = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        } catch( java.lang.Exception e){
            return false;
        }

        clear();

        return true;
    }
}