package com.servebbs.amazarashi.kangtangdotterzero.models.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;

public class IndexedBitmap {

    private Bitmap bitmap;

    public Bitmap getBitmap() { return bitmap; }

    public static IndexedBitmap createIndexedBitmap(int width, int height) {
        IndexedBitmap indexedBitmap = new IndexedBitmap();
        indexedBitmap.bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        return indexedBitmap;
    }
    public static IndexedBitmap createIndexedBitmap(IndexedBitmap src) {
        IndexedBitmap indexedBitmap = new IndexedBitmap();
        indexedBitmap.bitmap = Bitmap.createBitmap(src.bitmap);
        return indexedBitmap;
    }

    public void clear() {
        new Canvas(bitmap).drawColor(0x00000000, PorterDuff.Mode.SRC);
    }

    public Bitmap translateColor(Bitmap dst, ColorArray colorArray){
        Bitmap src = bitmap;

        int width = src.getWidth();
        int height = src.getHeight();
        int length = width*height;
        int[] buff = new int[length];

        src.getPixels(buff, 0, width, 0, 0, width, height);
        for( int idx=0; idx<length; idx++ ){
            buff[idx] = colorArray.getColor(buff[idx] >> 24);
        }
        dst.setPixels(buff, 0, width, 0, 0, width, height);
        return dst;
    }

    public void fromBitmap(Bitmap src, ColorArray colorArray){
        int width  = src.getWidth();
        int height = src.getHeight();
        int length = width*height;
        int[] buff = new int[length];

        src.getPixels(buff, 0, width, 0, 0, width, height);
        for( int idx=0; idx<length; idx++ ){
            buff[idx] = colorArray.findIndex(buff[idx]) << 24;
        }
        bitmap.setPixels(buff, 0, width, 0, 0, width, height);
    }

    private void createFromBitmap(Bitmap src, ColorArray colorArray){
        bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        fromBitmap(src, colorArray);
    }
}
