package com.Raiden.entities;
import com.Raiden.core.World;

import com.Raiden.audio.SoundEffect;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Laser bullet is derived from Bullet
 * Default damage: 300
 * Default speed(under cartesian coordinates): 10
 *
 * @author Zheng Qifeng
 * @version v0.1 (28 Dec 2018)
 */
public class LaserBullet extends Bullet {
    private Image img1;
    private JFrame frame;
    private World world;
    public LaserBullet(float posX, float posY, float speed, float angleInDegree, int damage, int wigth, int height, Image img1, JFrame frame, World world){
        super(posX, posY, speed, angleInDegree, damage, wigth, height, frame, world);
        this.img1 = img1;
        this.world = world;
    }
    public LaserBullet(float posX, float posY, float angleInDegree, int wigth, int height, Image img1, JFrame frame, World world){
        super(posX, posY, 10, angleInDegree, 300, wigth, height, frame, world);
        this.img1 = img1;
        this.world = world;
    }

    public void intersection(ArrayList<Bullet> bulletsToRemove, ArrayList<Enemy> enemiesToRemove){
        Rectangle bullet = new Rectangle((int)getPosX(), (int)getPosY(), getWidth(), getHeight());
        for (Enemy enemy : world.getEnemy()) {
            Rectangle currentEnemy = new Rectangle((int)enemy.getPosX(), (int)enemy.getPosY(), enemy.getWidth(), enemy.getHeight());
            if (bullet.intersects(currentEnemy)) {
                bulletsToRemove.add(this);
                enemy.setHealth(enemy.getHealth() - getDamage());
                if(enemy.getHealth() <= 0) {
                    enemiesToRemove.add(enemy);
                    enemy.isAlive = false;
                    world.player.setScore(world.player.getScore() + enemy.getScore());
                    world.deadActors.add(new DeadActor((int)enemy.getPosX(), (int)enemy.getPosY(), 50, 50, world.deadImg, world));
                    //world.playsound.run(World.MUSIC.LASERSHOOT, 0 ,0);
                    new Thread(new SoundEffect("sound/explosion.wav")).start();
                }
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
        if (new Random().nextBoolean()){
            g.drawImage(img1,
                    (int)super.getPosX(), (int)super.getPosY(),
                    (int)super.getPosX() + (int)super.getWidth(),
                    (int)super.getPosY() + (int)super.getHeight(),
                    sourcePoint[0], sourcePoint[1], sourcePoint[2], sourcePoint[3], null);
        } else {
            g.drawImage(img1,
                    (int)super.getPosX(), (int)super.getPosY(),
                    (int)super.getPosX() + (int)super.getWidth(),
                    (int)super.getPosY() + (int)super.getHeight(),
                    sourcePoint[4], sourcePoint[5], sourcePoint[6], sourcePoint[7], null);
        }
    }
}

