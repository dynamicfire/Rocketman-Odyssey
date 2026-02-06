package com.Raiden.entities;
import com.Raiden.core.World;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * This class is a superclass for all power-up
 * There are three types of power-up: shield,
 * wingman and laser transmitter.
 * Power-up's movement handled by itself.
 *
 * @author dynamicfire
 * @version v0.1 (28 Dec 2018)
 */
public class PowerUP {
    private float posX;
    private float posY;
    private float speedX, speedY;
    private int width;
    private int height; //image size

    private JFrame frame;
    private World world;

    /**
     * Constructor: it generates a power-up on an random position
     * at an random speed and an random flight direction.
     */
    public PowerUP(int width, int height, JFrame frame, World world){
        Random randomX = new Random();
        Random angleInDegreeGenerator = new Random();
        Random speedGenerator = new Random();
        int angleInDegree = angleInDegreeGenerator.nextInt(301);
        int speed = speedGenerator.nextInt(3) + 1;
        int x = randomX.nextInt(frame.getWidth());
        this.posX = x;
        this.posY = 0;
        this.speedX = (float)(speed * Math.cos(Math.toRadians(angleInDegree)));
        this.speedY = (float)(-speed * Math.sin(Math.toRadians(angleInDegree)));
        this.width = width;
        this.height = height;
        this.frame = frame;
        this.world = world;
    }

    /**
     * Moves the this object to the next position, reversing direction
     * if it hits one of the edges.
     */
    public void drift()
    {
        posX += speedX;
        posY += speedY;
        if (posX < 0)
        {
            posX = 0;
            speedX = -speedX;
        }
        if (posX + width >= frame.getWidth())
        {
            posX = frame.getWidth() - width;
            speedX = -speedX;
        }
        if (posY < 0)
        {
            posY = 0;
            speedY = -speedY;
        }
        if (posY + height >= frame.getHeight())
        {
            posY = frame.getHeight() - height;
            speedY = -speedY;
        }
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

}
