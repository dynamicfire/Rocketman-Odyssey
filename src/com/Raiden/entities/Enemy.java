package com.Raiden.entities;
import com.Raiden.core.World;

import com.Raiden.audio.SoundEffect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Aircraft{
    private int score;
    private Image img;
    private JFrame frame;
    private World world;
    boolean isAlive = true;

    public Enemy(float posX, float posY, int health, int width, int height, Image img, int score, JFrame frame, World world){
        super(posX, posY, (float)new Random().nextInt(20) - 10, (float)new Random().nextInt(10), health, width, height);
        this.img = img;
        this.frame = frame;
        this.world = world;
        this.score = score;

        ActionListener listener = new AutoFire(this, frame, world);
        Timer t =new Timer(500, listener);
        t.start();
    }

    public void fireAt(World world) {
        world.enemyBullets.add(new EnemyBullet(getPosX() + getWidth() / 2 - 3, getPosY() + getHeight(), 270, 15, 15, world.enemyBulletImg, frame, world));
    }

    /**
     * Moves the this object to the next position, reversing direction
     * if it hits one of the edges.
     */
    public void drift(ArrayList<Enemy> enemiesToRemove)
    {
        Rectangle enmey = new Rectangle((int)getPosX(), (int)getPosY(), getWidth(), getHeight());
        Rectangle player = new Rectangle((int)world.player.getPosX(), (int)world.player.getPosY(), world.player.getWidth(), world.player.getHeight());
        if (enmey.intersects(player)) {
            enemiesToRemove.add(this);
            this.isAlive = false;
            world.player.setHealth(world.player.getHealth() - 200);
            world.deadActors.add(new DeadActor((int)getPosX(), (int)getPosY(), 50, 50, world.deadImg, world));
            new Thread(new SoundEffect("sound/explosion.wav")).start();
            if (world.player.getHealth() <= 0){
                new Thread(new SoundEffect("sound/gameover.wav")).start();
                world.player.setHealth(0);
            }
        }

        setPosX(getPosX() + getSpeedX());
        setPosY(getPosY() + getSpeedY());
        if ((getPosX() < 0) || (getPosX() + getWidth() >= frame.getWidth()) ||
                (getPosY() < -getHeight()) || (getPosY() + getHeight() >= frame.getHeight())){
            enemiesToRemove.add(this);
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

    public int getScore() {
        return score;
    }

}

class AutoFire implements ActionListener {
    private JFrame frame;
    private Enemy enemy;
    private World world;
    public AutoFire(Enemy enemy, JFrame frame, World world){
        this.enemy = enemy;
        this.world = world;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (enemy.isAlive && enemy.getPosX() > 0 && enemy.getPosX() < frame.getWidth() - enemy.getWidth() && enemy.getPosY() > 0 && enemy.getPosY() < frame.getHeight()){
            enemy.fireAt(world);
        }
    }
}
