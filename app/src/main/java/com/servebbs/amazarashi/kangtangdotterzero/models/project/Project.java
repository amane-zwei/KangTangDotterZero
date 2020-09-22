package com.servebbs.amazarashi.kangtangdotterzero.models.project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.servebbs.amazarashi.kangtangdotterzero.KTDZApplication;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class Project {

    private int width;
    private int height;
    private int index;
    private ArrayList<Frame> frames = new ArrayList<>();
    private int backGroundColor;
    @Getter
    private Palette palette;

    private Bitmap destination;
    private Canvas canvas;

    public Project() {
        width = 64;
        height = 64;
        addFrame();
        index = 0;
        backGroundColor = 0xffffffff;
        palette = Palette.createDefault();

        destination = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(destination);
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

//    public Bitmap getBitmap() { return bmp; }
    public Rect getRect() {
        return new Rect(0,0,width,height );
    }

    public Frame getFrame() { return frames.get(index); }
    public void addFrame() { frames.add(new Frame(width, height)); }

    public Bitmap renderBitmap() {
        canvas.drawColor(backGroundColor);
        getFrame().render(canvas);
        return destination;
    }

    public void applyPalette(Palette palette) {
        if (!this.palette.equals(palette)) {
            this.palette = palette;
        }
    }

    public static Project get(Context context) {
        return KTDZApplication.get(context).getProjectContext().getProject();
    }
}
