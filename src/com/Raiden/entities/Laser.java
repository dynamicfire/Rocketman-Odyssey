package com.Raiden.entities;
import com.Raiden.core.World;


import com.Raiden.audio.SoundEffect;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Laser extends PowerUP {
    private Image img;
    private World world;

    public Laser(int width, int height, Image img, JFrame frame, World world) {
        super(width, height, frame, world);
        this.img = img;
        this.world = world;
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
        g.drawImage(img,
                (int) super.getPosX(), (int) super.getPosY(),
                (int) super.getPosX() + super.getWidth(),
                (int) super.getPosY() + super.getHeight(),
                sourcePoint[0], sourcePoint[1], sourcePoint[2], sourcePoint[3], null);
    }

    public void intersection(ArrayList<PowerUP> powerUPSToRemove){
        Rectangle powerup = new Rectangle((int)getPosX(), (int)getPosY(), getWidth(), getHeight());
        Rectangle player = new Rectangle((int)world.player.getPosX(), (int)world.player.getPosY(), world.player.getWidth(), world.player.getHeight());
        if (powerup.intersects(player)) {
            powerUPSToRemove.add(this);
            world.player.setAmmunition(1);
            world.player.setMagazine(20);
            new Thread(new SoundEffect("sound/laseron.wav")).start();
        }
    }
}