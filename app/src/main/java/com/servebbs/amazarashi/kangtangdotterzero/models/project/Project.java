package com.servebbs.amazarashi.kangtangdotterzero.models.project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.servebbs.amazarashi.kangtangdotterzero.models.histories.History;
import com.servebbs.amazarashi.kangtangdotterzero.models.histories.HistoryList;
import com.servebbs.amazarashi.kangtangdotterzero.models.primitive.DotColor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

public class Project {
    @JsonIgnore
    private int id;

    @Getter
    private int width;
    @Getter
    private int height;
    @JsonIgnore
    private int frameIndex;
    @Getter
    private boolean isIndexedColor;
    @Getter
    private final ArrayList<Frame> frames;
    @Getter
    private int backGroundColor;
    @Getter
    private Palette palette;

    @JsonIgnore
    private int paletteOnHistoryIndex;
    @JsonIgnore
    private final HistoryList history;

    @JsonIgnore
    private final Map<Integer, Layer> layerMap;

    @JsonIgnore
    private Bitmap destination;
    @JsonIgnore
    private Canvas canvas;

    public Project() {
        id = 0;
        width = 8;
        height = 8;
        isIndexedColor = false;

        paletteOnHistoryIndex = 0;
        history = new HistoryList();

        layerMap = new HashMap<>();

        frames = new ArrayList<>();
        addFrame();
        frameIndex = 0;
        backGroundColor = 0xffffffff;
        palette = Palette.createDefault();

        destination = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(destination);
    }

    public int generateId() {
        return id++;
    }

    public Rect createRect() {
        return new Rect(0, 0, width, height);
    }

    @JsonIgnore
    public Frame getFrame() {
        return frames.get(frameIndex);
    }

    public void addFrame() {
        frames.add(new Frame(this));
    }

    @JsonIgnore
    public Layer getLayer() { return getFrame().getLayer(); }

    public Bitmap renderBitmap() {
        canvas.drawColor(backGroundColor);
        getFrame().render(canvas);
        return destination;
    }

    public void applyPalette(Palette palette) {
        if (isIndexedColor && !this.palette.equals(palette)) {
            for (Layer layer : layerMap.values()) {
                layer.applyColorList(palette);
            }
            paletteOnHistoryIndex = history.getIndex();
        }
        this.palette = palette;
    }

    public void addHistory(History history) {
        this.history.add(history);
        Layer layer = layerMap.get(history.getLayerId());
        if (layer != null) {
            layer.write(this.history.getIndex());
        }
    }

    public int restoreLayerHistory(int historyIndex) {
        int maxIndex = 0;
        for (Layer layer : layerMap.values()) {
            int tmpIndex = layer.restoreLayerHistory(historyIndex);
            if (isIndexedColor && tmpIndex < paletteOnHistoryIndex) {
                layer.applyColorList(palette);
            }
            maxIndex = Math.max(tmpIndex, maxIndex);
        }
        return maxIndex;
    }

    public boolean unRedo(int delta) {
        return history.applyHistory(this, delta);
    }

    public void putLayer(Layer layer) {
        layerMap.put(layer.getId(), layer);
    }

    public void removeLayer(Layer layer) {
        layerMap.remove(layer.getId());
    }

    public Layer findLayer(int id) {
        return layerMap.get(id);
    }

    public Collection<Layer> layers() { return layerMap.values(); }

    @JsonIgnore
    public DotColor getColor() {
        return palette.getColor();
    }

    @JsonIgnore
    public DotColor getClearColor() {
        if (isIndexedColor) {
            return palette.getColor(0);
        } else {
            return DotColor.fromColorValue(0x0);
        }
    }

    @JsonIgnore
    public boolean isOut(int x, int y) {
        int w = getWidth();
        int h = getHeight();

        return x < 0 || w <= x || y < 0 || h <= y;
    }

    public static Project get(Context context) {
        return ProjectContext.get(context).getProject();
    }
}
