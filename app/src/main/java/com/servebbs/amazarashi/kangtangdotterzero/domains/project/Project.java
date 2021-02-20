package com.servebbs.amazarashi.kangtangdotterzero.domains.project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.servebbs.amazarashi.kangtangdotterzero.domains.histories.History;
import com.servebbs.amazarashi.kangtangdotterzero.domains.histories.HistoryList;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotColor;
import com.servebbs.amazarashi.kangtangdotterzero.domains.primitive.DotColorValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

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
    @Setter
    private boolean isIndexedColor;
    @Getter
    @Setter
    private ArrayList<Frame> frames;
    @Getter
    @Setter
    private DotColorValue backGroundColor;

    @Getter
    @JsonIgnore
    private Palette palette;

    @JsonIgnore
    private int paletteOnHistoryIndex;
    @Getter
    @JsonIgnore
    private HistoryList history;

    @JsonIgnore
    private final Map<Integer, Layer> layerMap;

    @JsonIgnore
    private Bitmap destination;
    @JsonIgnore
    private Canvas canvas;

    public static Project createDefault() {
        return create(
                8,
                8,
                new DotColorValue(0xffffffff),
                false);
    }

    public static Project create(
            int width,
            int height,
            DotColorValue backgroundColor,
            boolean isIndexedColor) {
        Project project = new Project();
        project.width = width;
        project.height = height;
        project.isIndexedColor = isIndexedColor;
        project.backGroundColor = backgroundColor;

        project.frames = new ArrayList<>();
        project.addFrame();
        project.frameIndex = 0;

        project.palette = Palette.createDefault(isIndexedColor);

        project.createDestination();

        return project;
    }

    private Project() {
        id = 0;
        paletteOnHistoryIndex = 0;

        history = new HistoryList();
        layerMap = new HashMap<>();
    }

    public Project restore(Palette palette, Map<String, Bitmap> bitmaps) throws IOException{

        history = new HistoryList();

        this.palette = palette;

        for (Frame frame : frames) {
            frame.restore(this);

            for (Layer layer : frame.getLayers()) {
                Bitmap bitmap = bitmaps.get(Integer.toString(layer.getId()));
                if (bitmap == null) {
                    throw new IOException("image not found: " + layer.getId());
                }
                layer.restore(palette, bitmap, isIndexedColor);
                layerMap.put(layer.getId(), layer);
            }
        }

        createDestination();
        return this;
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
    public Layer getLayer() {
        return getFrame().getLayer();
    }

    public Bitmap renderBitmap() {
        canvas.drawColor(backGroundColor.getValue());
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

    public Collection<Layer> layers() {
        return layerMap.values();
    }

    @JsonIgnore
    public DotColor getColor() {
        int intValue = palette.getColor().getValue();
        return isIndexedColor
                ? DotColor.create(intValue, palette.getIndex())
                : DotColor.fromColorValue(intValue);
    }

    @JsonIgnore
    public DotColor getClearColor() {
        return isIndexedColor
                ? DotColor.create(palette.getColor(0).getValue(), 0)
                : DotColor.fromColorValue(0x00);
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

    private void createDestination() {
        destination = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(destination);
    }
}
