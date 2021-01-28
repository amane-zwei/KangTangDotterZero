package com.servebbs.amazarashi.kangtangdotterzero.domains;

public class ScreenNormalizer {
    int positionX;
    int positionY;

    int targetX;
    int targetY;

    int rate;

    int originX;
    int originY;

    public ScreenNormalizer() {
        positionX = 0;
        positionY = 0;

        targetX = 0;
        targetY = 0;

        originX = 96;
        originY = 96;

        rate = 4*65536;
    }

    public int getPaperX(float screenX){ return (int)( (screenX-originX)*65536+ targetX*rate )/rate; }
    public int getPaperY(float screenY){ return (int)( (screenY-originY)*65536+ targetY*rate )/rate; }
    public int getScreenX(int paperX){ return (paperX*rate-(int)(targetX*rate))/65536 + originX; }
    public int getScreenY(int paperY){ return (paperY*rate-(int)(targetY*rate))/65536 + originY; }


}
