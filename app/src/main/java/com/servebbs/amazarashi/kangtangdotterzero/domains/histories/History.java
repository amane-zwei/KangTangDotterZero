package com.servebbs.amazarashi.kangtangdotterzero.domains.histories;

import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Layer;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Palette;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class History {
    @Getter
    private int layerId;

    public History(Layer layer) {
        this.layerId = layer.getId();
    }

    abstract public void draw(Layer layer, Palette palette);
}
