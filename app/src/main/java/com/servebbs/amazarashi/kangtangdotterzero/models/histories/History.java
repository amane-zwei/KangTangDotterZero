package com.servebbs.amazarashi.kangtangdotterzero.models.histories;

import android.graphics.Canvas;

import com.servebbs.amazarashi.kangtangdotterzero.models.project.Layer;

import lombok.Getter;

public abstract class History {
    @Getter
    private final int layerId;

    public History(Layer layer) {
        this.layerId = layer.getId();
    }

    public void draw(Layer layer) {
        draw(new Canvas(layer.getDisplay()));
        layer.touch();
    }
    abstract public void draw(Canvas canvas);
}
