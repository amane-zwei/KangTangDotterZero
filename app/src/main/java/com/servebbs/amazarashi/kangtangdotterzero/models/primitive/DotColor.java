package com.servebbs.amazarashi.kangtangdotterzero.models.primitive;

import com.servebbs.amazarashi.kangtangdotterzero.models.bitmap.IndexedBitmap;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Palette;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DotColor {
    private int value;
    private final int index;

    public static DotColor fromColorValue(int value) {
        return new DotColor(value, 0);
    }

    public static DotColor fromIndex(int value, int index) {
        return new DotColor(value, IndexedBitmap.toSaveIndex(index));
    }

    public int intValue() {
        return value;
    }

    public int plainIndex() {
        return IndexedBitmap.toPlainIndex(index);
    }

    public int saveIndex() {
        return index;
    }

    public boolean isIndexedColor() {
        return (index & 0xff000000) != 0;
    }

    public DotColor applyPalette(Palette palette) {
        value = palette.getColor(plainIndex());
        return this;
    }
}
