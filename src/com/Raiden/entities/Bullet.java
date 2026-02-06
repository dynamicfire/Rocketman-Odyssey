package com.Raiden.entities;
import com.Raiden.core.World;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class is a superclass for all bullet from
 * both enemy and player. There are three types of
 * bullet: normal one with blue color, damage-enhanced
 * with orange color and laser.
 * Bullet's movement should not handled by itself.
 *
 * @author dynamicfire
 * @version v0.1 (28 Dec 2018)
 */
public class Bullet {
    private float posX;
    private float posY;
    private float speedX, speedY;
    private int damage;
    private int width;
    private int height; //image size
    private boolean isCrossed = false;

    private JFrame frame;
    private World world;


    /**
     * Constructor: For user friendliness, user specifies velocity in speed and
     * moveAngle in usual Cartesian coordinates. Need to convert to speedX and
     * speedY in Java graphics coordinates for ease of operation.
     *
     * @param posX : x-position of the Upper left corner of the bullet's image.
     * @param posY : y-position of the Upper left corner of the bullet's image.
     * @param angleInDegree : angle of the bullet, starting from the first quadrant.
     */
    public Bullet(float posX, float posY, float speed, float angleInDegree,
                  int damage, int width, int height, JFrame frame, World world){
        this.posX = posX;
        this.posY = posY;
        this.speedX = (float)(speed * Math.cos(Math.toRadians(angleInDegree)));
        this.speedY = (float)(-speed * Math.sin(Math.toRadians(angleInDegree)));
        this.damage = damage;
        this.width = width;
        this.height = height;
        this.frame = frame;
        this.world = world;
    }

    public void advance(ArrayList<Bullet> bulletsToRemove){
        if ((posX < 0) || (posX + width >= frame.getWidth()) ||
                (posY < 0) || (posY + height >= frame.getHeight())){
            //world.getBullet().remove(this); Avoiding ConcurrentModificationException when removing objects in a loop
            bulletsToRemove.add(this);
            return;
        }
        posX+=speedX;
        posY+=speedY;
    }

    /** Return the magnitude of speed. */
    public float getSpeed() {
        return (float)Math.sqrt(speedX * speedX + speedY * speedY);
    }
    public float getSpeedX(){return speedX;}
    public float getSpeedY(){return speedY;}

    /** Return position coordinates. */
    public float getPosX(){ return posX; }
    public float getPosY(){ return posY; }
    public float[] getPos(){
        float pos[] = {posX, posY};
        return pos;
    }

    public int getWidth(){return width;}
    public int getHeight(){return height;}

    public int getDamage(){ return damage; }

    /** Return the direction of movement in degrees (counter-clockwise). */
    public float getMoveAngle() {
        return (float)Math.toDegrees(Math.atan2(-speedY, speedX));
    }

    public boolean setPosX(float posX){
        this.posX = posX;
        return true;
    }

    public boolean setPosY(float posY){
        this.posY = posY;
        return true;
    }
    public boolean setSpeed(float speed, float angleInDegree){
        this.speedX = (float)(speed * Math.cos(Math.toRadians(angleInDegree)));
        this.speedY = (float)(-speed * Math.sin(Math.toRadians(angleInDegree)));
        return true;
    }

    public boolean setDamage(int damage){
        this.damage = damage;
        return true;
    }

    /** Draw itself using the given graphics context.
     *  Draw partial scaled image. SourcePoint1 & 2 are two endpoints of diagonal of a rectangle.
     *  Overridden by its subclass.
     *
     *  @param sourcePoint : the x, y coordinates of the first & second corner of the source rectangle.
     *                     sourcePoint[0..3] is sourcePoint1, sourcePoint[4..7] is sourcePoint2
     */
    public void draw(Graphics g, int[] sourcePoint) {}

}
