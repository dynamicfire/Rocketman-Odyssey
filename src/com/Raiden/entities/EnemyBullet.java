package com.Raiden.entities;
import com.Raiden.core.World;

import com.Raiden.audio.SoundEffect;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EnemyBullet extends Bullet{
    private Image img;
    private JFrame frame;
    private World world;

    public EnemyBullet(float posX, float posY, float speed, float angleInDegree, int damage, int wigth, int height, Image img, JFrame frame, World world){
        super(posX, posY, speed, angleInDegree, damage, wigth, height, frame, world);
        this.img = img;
        this.frame = frame;
        this.world =world;
    }
    public EnemyBullet(float posX, float posY, float angleInDegree, int wigth, int height, Image img, JFrame frame, World world){
        super(posX, posY, 10, angleInDegree, 50, wigth, height, frame, world);
        this.img = img;
        this.frame = frame;
        this.world =world;
    }

    public void attack(ArrayList<EnemyBullet> enemyBulletsToRemove){
        if ((getPosX() < 0) || (getPosX() + getWidth() >= frame.getWidth()) ||
                (getPosY() < 0) || (getPosY() + getHeight() >= frame.getHeight())){
            //world.getBullet().remove(this); Avoiding ConcurrentModificationException when removing objects in a loop
            enemyBulletsToRemove.add(this);
            return;
        }
        setPosX(getPosX()+getSpeedX());
        setPosY(getPosY()+getSpeedY());
    }

    public void intersection(ArrayList<EnemyBullet> enemyBulletsToRemove, Player player){
        Rectangle bullet = new Rectangle((int)getPosX(), (int)getPosY(), getWidth(), getHeight());
        Rectangle currentPlayer = new Rectangle((int)player.getPosX(), (int)player.getPosY(), player.getWidth(), player.getHeight());
        if (bullet.intersects(currentPlayer)) {
            enemyBulletsToRemove.add(this);
            player.setHealth(player.getHealth() - getDamage());
            if (player.getHealth() <= 0){
                new Thread(new SoundEffect("sound/gameover.wav")).start();
                player.setHealth(0);
            }
            else {
                new Thread(new SoundEffect("sound/hurt.wav")).start();
            }
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
        g.drawImage(img,
                (int) super.getPosX(), (int) super.getPosY(),
                (int) super.getPosX() + (int) super.getWidth(),
                (int) super.getPosY() + (int) super.getHeight(),
                sourcePoint[0], sourcePoint[1], sourcePoint[2], sourcePoint[3], null);

    }
}
