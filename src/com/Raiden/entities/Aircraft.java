package com.Raiden.entities;
import com.Raiden.core.World;

import java.awt.*;

/**
 * This class is a superclass for all aircraft.
 * Aircraft's movement handled by itself.
 *
 * @author dynamicfire
 * @version v0.1 (28 Dec 2018)
 */
public class Aircraft {
    private float posX;
    private float posY;
    private float speedX, speedY;
    private int health;
    private int width;
    private int height; //image size

    /**
     * @param posX : x-position of the Upper left corner of the aircraft's image.
     * @param posY : y-position of the Upper left corner of the aircraft's image.
     */
    public Aircraft(float posX, float posY, float speedX, float speedY, int health, int width, int height){
        this.posX = posX;
        this.posY = posY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.health = health;
        this.width = width;
        this.height = height;
    }

    /** Draw itself using the given graphics context.
     *  Draw partial scaled image. SourcePoint1 & 2 are two endpoints of diagonal of a rectangle.
     *  Overridden by its subclass.
     *
     *  @param sourcePoint : the x, y coordinates of the first & second corner of the source rectangle.
     *                     sourcePoint[0..3] is sourcePoint1, sourcePoint[4..7] is sourcePoint2
     */
    public void draw(Graphics g, int[] sourcePoint) {}

    /** Return position coordinates. */
    public float getPosX(){ return posX; }
    public float getPosY(){ return posY; }
    public float[] getPos(){
        float pos[] = {posX, posY};
        return pos;
    }

    public int getWidth(){return width;}
    public int getHeight(){return height;}

    public int getHealth(){ return health; }

    public float getSpeedX(){return speedX;}
    public float getSpeedY(){return speedY;}

    public boolean setPosX(float posX){
        this.posX = posX;
        return true;
    }

    public boolean setPosY(float posY){
        this.posY = posY;
        return true;
    }

    public boolean setHealth(int health){
        this.health = health;
        return true;
    }
}
