package com.servebbs.amazarashi.kangtangdotterzero.domains.lowlevel;

public class Point {
    public Point(){}
    public Point(int x, int y){ this.x=x; this.y=y; };
    public Point(Point src){ x=src.x; y=src.y; }
    public int x;
    public int y;
}
