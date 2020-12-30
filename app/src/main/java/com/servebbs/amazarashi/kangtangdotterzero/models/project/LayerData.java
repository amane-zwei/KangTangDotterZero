package com.servebbs.amazarashi.kangtangdotterzero.models.project;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.servebbs.amazarashi.kangtangdotterzero.models.bitmap.ColorList;
import com.servebbs.amazarashi.kangtangdotterzero.models.bitmap.IndexedBitmap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class LayerData {
    public static final Bitmap.Config bitmapConfig = Bitmap.Config.ARGB_8888;

    @JsonIgnore
    @Getter
    protected Bitmap display;
    @JsonIgnore
    @Getter
    protected IndexedBitmap indexed;
    @JsonIgnore
    protected int historyIndex;

    @JsonIgnore
    public int getWidth() {
        return display.getWidth();
    }

    @JsonIgnore
    public int getHeight() {
        return display.getHeight();
    }

    @JsonIgnore
    public boolean isIndexedColor() {
        return indexed != null;
    }

    public void useIndexedColor() {
        indexed = IndexedBitmap.empty();
    }

    public LayerData restore(ColorList colorList, Bitmap src, boolean isIndexedColor) {
      if (isIndexedColor) {
          display = Bitmap.createBitmap(src.getWidth(), src.getHeight(), bitmapConfig);
          indexed = IndexedBitmap.create(src, colorList);
      } else {
          display = src;
          indexed = null;
      }
      historyIndex = 0;
      return this;
    }

    public LayerData restore(LayerData src) {
        display = src.display.copy(bitmapConfig, true);
        if (src.indexed == null) {
            indexed = null;
        } else {
            indexed = src.indexed.copy();
        }
        historyIndex = src.historyIndex;
        return this;
    }

    public LayerData copy() {
        return new LayerData().restore(this);
    }

    public void useIndexed(ColorList colorList) {
        indexed = IndexedBitmap.create(display, colorList);
    }

    public void stopIndexed(ColorList colorList) {
        if (indexed != null) {
            display = indexed.translateColor(display, colorList);
            indexed = null;
        }
    }

    public void clear() {
        new Canvas(display).drawColor(0x00000000, PorterDuff.Mode.SRC);
        if (indexed != null) {
            indexed.clear();
        }
    }

    public void resize(int width, int height) {
        display = Bitmap.createBitmap(width, height, bitmapConfig);
        if (indexed != null) {
            indexed.resize(width, height);
        }
    }

    public void applyColorList(ColorList colorList) {
        indexed.translateColor(display, colorList);
    }

    public boolean applyBitmap(Bitmap src) {
        if (src == null) {
            return false;
        }
        this.display = src.copy(bitmapConfig, true);
        this.indexed = null;
        return true;
    }
}