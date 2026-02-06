package com.Raiden.entities;
import com.Raiden.core.World;

import com.Raiden.audio.SoundEffect;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Player extends Aircraft {
    private int score = 0;
    private Image img;
    private JFrame frame;
    private int ammunition = 1; //Ammunition type: 0 for normal, 1 for laser
    private int magazine = 20; //10 rounds laser at beginning
    private boolean isPaused = false;
    private boolean isPlaying = false;

    public Player(float posX, float posY, float speedX, float speedY, int health, int width, int height, Image img, JFrame frame, World world){
        super(posX, posY, speedX, speedY, health, width, height);
        this.img = img;
        this.frame = frame;

        //ActionListener listener = new AutoFire(this, world);
        //Timer t =new Timer(200, listener);
        //t.start();
    }



    public void fireAt(World world) {
        if (magazine == 0) {
            ammunition = 0;
        }
        if (ammunition == 0){
            world.bullets.add(new NormalBullet(getPosX() + getWidth()/2 - 3, getPosY(), 90, 15, 15, world.bulletImg, frame, world));
            new Thread(new SoundEffect("sound/shoot.wav")).start();
        }
        if (ammunition == 1 && magazine >= 1){
            world.bullets.add(new LaserBullet(getPosX() - getWidth()/2 - 8, getPosY(), 45, 15, 39, world.bulletImg, frame, world));
            world.bullets.add(new LaserBullet(getPosX() + getWidth() + 3, getPosY(), 135, 15, 39, world.bulletImg, frame, world));
            world.bullets.add(new LaserBullet(getPosX() - getWidth()/2 - 8, getPosY(), 70, 15, 39, world.bulletImg, frame, world));
            world.bullets.add(new LaserBullet(getPosX() + getWidth() + 3, getPosY(), 110, 15, 39, world.bulletImg, frame, world));

            world.bullets.add(new LaserBullet(getPosX() + getWidth()/2 - 8, getPosY(), 90, 15, 39, world.bulletImg, frame, world));
            magazine--;
            new Thread(new SoundEffect("sound/lasershoot.wav")).start();
        }
    }

    public void boardDetection() {
        if (super.getPosX() < 0)
        {
            super.setPosX(0);
        }
        if (super.getPosX() + super.getWidth() >= frame.getWidth())
        {
            super.setPosX(frame.getWidth() - super.getWidth());
        }
        if (super.getPosY() < 0)
        {
            super.setPosY(0);
        }
        if (super.getPosY() + super.getHeight() >= frame.getHeight())
        {
            super.setPosY(frame.getHeight() - super.getHeight());
        }
    }

    /** Draw itself using the given graphics context.
     *  Draw partial scaled image. SourcePoint1 & 2 are two endpoints of diagonal of a rectangle.
     *  Called by canvas.
     *
     *  @param sourcePoint : the x, y coordinates of the first & second corner of the source rectangle.
     *                     sourcePoint[0, 1] is sourcePoint1, sourcePoint[2, 3] is sourcePoint2
     */
    @Override
    public void draw(Graphics g, int[] sourcePoint) {
        Random random = new Random();
        int index = random.nextInt(2) * 4;
        g.drawImage(img,
                (int) super.getPosX(), (int) super.getPosY(),
                (int) super.getPosX() + super.getWidth(),
                (int) super.getPosY() + super.getHeight(),
                sourcePoint[index], sourcePoint[index + 1], sourcePoint[index + 2], sourcePoint[index + 3], null);
    }

    public int getScore() {return score;}
    public void setScore(int score) {this.score = score;}

    public boolean isPaused() {return isPaused;}
    public void setPaused(boolean paused){isPaused = paused;}
    public boolean isPlaying() {return isPlaying;}
    public void setPlaying(boolean playing){isPlaying = playing;}
    public void setAmmunition(int ammunition) {this.ammunition = ammunition;}
    public void setMagazine(int magazine){this.magazine += magazine;}
}

