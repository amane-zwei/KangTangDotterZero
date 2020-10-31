package com.servebbs.amazarashi.kangtangdotterzero.models.project;

import android.graphics.Bitmap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.servebbs.amazarashi.kangtangdotterzero.models.bitmap.IndexedBitmap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class LayerData {
    public final Bitmap.Config bitmapConfig = Bitmap.Config.ARGB_8888;

    @JsonIgnore
    @Getter
    protected Bitmap display;
    @JsonIgnore
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
}