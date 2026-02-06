package com.Raiden.graphics;

import javax.swing.*;
import java.awt.*;

public class Background {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private Image img;

    public Background(int index, int width, int height, Image img, JFrame frame){
        posX = 0;
        posY = frame.getHeight() - frame.getHeight()*(index+1);
        this.width = width;
        this.height = height;
        this.img = img;
    }
    public Background(int width, int height, Image img){
        posX = 0;
        posY = 0;
        this.width = width;
        this.height = height;
        this.img = img;
    }
    public void move(JFrame frame, int total){
        if (posY == frame.getHeight()){
            posY = -frame.getHeight() *(total - 1);
        }
        posY++;
    }

    /** Draw itself using the given graphics context.
     *  Draw partial scaled image. SourcePoint1 & 2 are two endpoints of diagonal of a rectangle.
     *  Called by canvas.
     *
     *  @param sourcePoint1x : the x coordinate of the first corner of the source rectangle.
     *  @param sourcePoint1y : the y coordinate of the first corner of the source rectangle.
     *  @param sourcePoint2x : the x coordinate of the second corner of the source rectangle.
     *  @param sourcePoint2y : the y coordinate of the second corner of the source rectangle.
     */
    public void draw(Graphics g, int sourcePoint1x, int sourcePoint1y, int sourcePoint2x, int sourcePoint2y) {
        g.drawImage(img, (int)posX, (int)posY, (int)posX + width, (int)posY + height,
                sourcePoint1x, sourcePoint1y, sourcePoint2x, sourcePoint2y, null);
    }
}
