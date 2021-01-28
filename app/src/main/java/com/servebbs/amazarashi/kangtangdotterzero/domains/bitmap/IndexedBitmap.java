package com.servebbs.amazarashi.kangtangdotterzero.domains.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class IndexedBitmap {
    public static final Bitmap.Config bitmapConfig = Bitmap.Config.ARGB_8888;

    @Getter
    private Bitmap bitmap;

    public static IndexedBitmap empty() {
        return new IndexedBitmap();
    }

    public static IndexedBitmap createIndexedBitmap(int width, int height) {
        return new IndexedBitmap().resize(width, height);
    }

    public static IndexedBitmap createIndexedBitmap(IndexedBitmap src) {
        IndexedBitmap indexedBitmap = new IndexedBitmap();
        indexedBitmap.bitmap = Bitmap.createBitmap(src.bitmap);
        return indexedBitmap;
    }

    public static IndexedBitmap create(Bitmap src, ColorList colorList) {
        IndexedBitmap bitmap = createIndexedBitmap(src.getWidth(), src.getHeight());
        bitmap.fromBitmap(src, colorList);
        return bitmap;
    }

    public IndexedBitmap resize(int width, int height) {
        bitmap = Bitmap.createBitmap(width, height, bitmapConfig);
        return this;
    }

    public void clear() {
        new Canvas(bitmap).drawColor(0x00000000, PorterDuff.Mode.SRC);
    }

    public Bitmap translateColor(Bitmap dst, ColorList colorList) {
        Bitmap src = bitmap;

        int width = src.getWidth();
        int height = src.getHeight();
        int length = width * height;
        int[] buff = new int[length];

        src.getPixels(buff, 0, width, 0, 0, width, height);
        for (int idx = 0; idx < length; idx++) {
            buff[idx] = colorList.getColor(toPlainIndex(buff[idx])).getValue();
        }
        dst.setPixels(buff, 0, width, 0, 0, width, height);
        return dst;
    }

    public void fromBitmap(Bitmap src, ColorList colorList) {
        int width = src.getWidth();
        int height = src.getHeight();
        int length = width * height;
        int[] buff = new int[length];

        src.getPixels(buff, 0, width, 0, 0, width, height);
        for (int idx = 0; idx < length; idx++) {
            buff[idx] = toSaveIndex(colorList.findIndex(buff[idx]));
        }
        bitmap.setPixels(buff, 0, width, 0, 0, width, height);
    }

    public IndexedBitmap copy() {
        return new IndexedBitmap(bitmap.copy(Bitmap.Config.ARGB_8888, true));
    }

    private void createFromBitmap(Bitmap src, ColorList colorList) {
        bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        fromBitmap(src, colorList);
    }

    public static int toSaveIndex(int index) {
        return index | 0xff000000;
    }

    public static int toPlainIndex(int saveIndex) {
        return saveIndex & 0x00ffffff;
    }
}
