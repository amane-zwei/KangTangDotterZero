package com.servebbs.amazarashi.kangtangdotterzero.models.histories;

import com.servebbs.amazarashi.kangtangdotterzero.models.project.Layer;
import com.servebbs.amazarashi.kangtangdotterzero.models.project.Palette;

import lombok.Getter;

public abstract class History {
    @Getter
    private final int layerId;

    public History(Layer layer) {
        this.layerId = layer.getId();
    }

    abstract public void draw(Layer layer, Palette palette);
}
