package com.servebbs.amazarashi.kangtangdotterzero.domains.histories;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Layer;
import com.servebbs.amazarashi.kangtangdotterzero.domains.project.Palette;
import com.servebbs.amazarashi.kangtangdotterzero.domains.tools.Bucket;
import com.servebbs.amazarashi.kangtangdotterzero.domains.tools.Pen;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "pen", value = Pen.PenHistory.class),
        @JsonSubTypes.Type(name = "bucket", value = Bucket.BucketHistory.class)
})
public abstract class History {
    @Getter
    private int layerId;

    public History(Layer layer) {
        this.layerId = layer.getId();
    }

    abstract public void draw(Layer layer, Palette palette);
    public void restore() {}
}
