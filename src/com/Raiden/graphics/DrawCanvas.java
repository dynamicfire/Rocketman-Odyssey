package com.Raiden.graphics;

import com.Raiden.entities.*;
import com.Raiden.graphics.*;
import com.Raiden.core.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class DrawCanvas extends JPanel implements Runnable {
    private ArrayList<PowerUP> powerups;
    private ArrayList<Bullet> bullets;
    private ArrayList<Enemy> enemies;
    private ArrayList<EnemyBullet> enemyBullets;
    private ArrayList<Cloud> clouds;
    private ArrayList<Background> backgrounds;
    private ArrayList<DeadActor> deadActors;
    private Player player;
    private World world;
    private JFrame frame;
    private Font font;

    public DrawCanvas(JFrame frame, World world){
        powerups = world.getPowerUPS();
        bullets = world.getBullet();
        enemies = world.getEnemy();
        player = world.getPlayer();
        enemyBullets = world.getEnemyBullets();
        clouds = world.getClouds();
        backgrounds = world.getBackground();
        deadActors = world.getDeadActors();
        this.world = world;
        this.frame = frame;
        try{
            //System.out.println(this.getClass().getClassLoader().getResource("font/8-BIT-WONDER.TTF"));
            font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getClassLoader().getResourceAsStream("font/8-BIT-WONDER.TTF"));
        } catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ArrayList<Background> localBackground = new ArrayList<Background>(backgrounds);
        for(Background currentBackground : localBackground) {
            currentBackground.draw(g, 0,0,256,272);
        }
        ArrayList<PowerUP> localPowerUPS = new ArrayList<PowerUP>(powerups);
        for(PowerUP currentPowerUP : localPowerUPS) {
            if (currentPowerUP instanceof Shield) {
                int[] sourcePoint = {0, 0, 16, 16};
                currentPowerUP.draw(g, sourcePoint);
            } else if (currentPowerUP instanceof Laser) {
                int[] sourcePoint = {0, 16, 16, 32};
                currentPowerUP.draw(g, sourcePoint);
            }
        }
        ArrayList<Bullet> localBullet = new ArrayList<Bullet>(bullets);
        for(Bullet currentBullet : localBullet) {
            if (currentBullet instanceof NormalBullet) {
                int[] sourcePoint = {6, 7, 12, 12, 21, 7, 26, 12};
                currentBullet.draw(g, sourcePoint);
            }
            else {
                int[] sourcePoint = {6, 18, 10, 30, 20, 18, 24, 30};
                currentBullet.draw(g, sourcePoint);
            }
        }
        ArrayList<Enemy> localEnemies = new ArrayList<Enemy>(enemies);
        for (Enemy currentEnemy : (ArrayList<Enemy>) localEnemies) {
            int[] sourcePoint = {3, 2, 28, 31, 35, 2, 60, 31};
            currentEnemy.draw(g, sourcePoint);
        }
        ArrayList<EnemyBullet> localEnemyBullet = new ArrayList<EnemyBullet>(enemyBullets);
        for (EnemyBullet currentEnemyBullet : localEnemyBullet)
        {
            int[] sourcePoint = {0, 0, 5, 5};
            currentEnemyBullet.draw(g, sourcePoint);
        }
        ArrayList<DeadActor> localDeadActor = new ArrayList<DeadActor>(deadActors);
        for (DeadActor currentDeadActor : localDeadActor)
        {
            int[] sourcePoint = {0, 0, 15, 15, 16, 0, 31, 15, 32, 0, 47, 15, 48, 0, 63, 15, 64, 0, 79, 15};
            currentDeadActor.draw(g, sourcePoint);
        }
        ArrayList<Cloud> localCloud = new ArrayList<Cloud>(clouds);

        {
            int[] sourcePoint = {2, 0, 13, 23, 2, 24, 13, 47};
            player.draw(g, sourcePoint);
        }

        for (Cloud currentCloud : localCloud) {
            int[] sourcePoint = {0, 0, 256, 103};
            currentCloud.draw(g, sourcePoint);
        }

        if (player.getHealth() <= 0) {
            font = font.deriveFont(45F);
            g.setFont(font);
            g.setColor(Color.RED);
            g.drawString("GAME OVER", getWidth() / 2 - 200, getHeight() / 2);
            font = font.deriveFont(15F);
            g.setFont(font);
            g.drawString("PRESS F2 TO TRY AGAIN", getWidth() / 2 - 200, getHeight() / 2 + 50);
        }
        if (player.isPaused()) {
            font = font.deriveFont(35F);
            g.setFont(font);
            g.setColor(new Color(171, 78, 28));
            g.drawString("PRESS SPACE", getWidth() / 2 - 200, getHeight() / 2);
            g.drawString("TO CONTINUE", getWidth() / 2 - 120, getHeight() / 2 + 60);
        }
        if(!player.isPlaying()) {
            font = font.deriveFont(55F);
            g.setFont(font);
            g.setColor(new Color(8, 65, 129));
            g.drawString("ROCKETMAN", getWidth() / 2 - 260, getHeight() / 2 - 110);
            g.drawString("Odyssey", getWidth() / 2 - 120, getHeight() / 2 - 50);
            font = font.deriveFont(35F);
            g.setFont(font);
            g.setColor(new Color(171, 78, 28));
            g.drawString("PRESS F1", getWidth() / 2 - 120, getHeight() / 2 + 50);
            g.drawString("TO START", getWidth() / 2 - 120, getHeight() / 2 + 110);
        }
        if(player.isPlaying()) {
            font = font.deriveFont(20F);
            g.setFont(font);
            g.setColor(new Color(129, 31, 12));
            g.drawString("SCORE " + player.getScore(), 10, 30);
            g.drawString("HP " + player.getHealth(), 10, 60);
        }
    }

    @Override
    public void run() {
        while (true) {
            if(player.getHealth() > 0 && !player.isPaused()) {
                //System.out.println(powerups.size());
                //Avoiding ConcurrentModificationException by creating a copy!
                ArrayList<Background> localBackground = new ArrayList<Background>(backgrounds);
                for (Background currentBackground : localBackground) {
                    currentBackground.move(frame, localBackground.size());
                }
                ArrayList<PowerUP> localPowerUPS = new ArrayList<PowerUP>(powerups);
                for (PowerUP currentPowerUP : localPowerUPS) {
                    currentPowerUP.drift();
                    if (currentPowerUP instanceof Shield) {
                        ((Shield) currentPowerUP).intersection(world.getPowerUPSToRemove());
                    } else if (currentPowerUP instanceof Laser) {
                        ((Laser) currentPowerUP).intersection(world.getPowerUPSToRemove());
                    }
                }
                ArrayList<Bullet> localBullet = new ArrayList<Bullet>(bullets);
                for (Bullet currentBullet : localBullet) {
                    currentBullet.advance(world.getBulletsToRemove());
                    if (currentBullet instanceof NormalBullet) {
                        ((NormalBullet) currentBullet).intersection(world.getBulletsToRemove(), world.getEnemiesToRemove());
                    } else {
                        ((LaserBullet) currentBullet).intersection(world.getBulletsToRemove(), world.getEnemiesToRemove());
                    }
                }
                ArrayList<Enemy> localEnemies = new ArrayList<Enemy>(enemies);
                for (Enemy currentEnemy : localEnemies) {
                    currentEnemy.drift(world.getEnemiesToRemove());
                }
                ArrayList<EnemyBullet> localEnemyBullet = new ArrayList<EnemyBullet>(enemyBullets);
                for (EnemyBullet currentBullet : localEnemyBullet) {
                    currentBullet.attack(world.getEnemyBulletsToRemove());
                    currentBullet.intersection(world.getEnemyBulletsToRemove(), player);
                }
                ArrayList<Cloud> localCloud = new ArrayList<Cloud>(clouds);
                for (Cloud currentCloud : localCloud) {
                    currentCloud.move(world.getCloudsToRemove());
                }

                bullets.removeAll(world.getBulletsToRemove());
                powerups.removeAll(world.getPowerUPSToRemove());
                enemies.removeAll(world.getEnemiesToRemove());
                enemyBullets.removeAll(world.getEnemyBulletsToRemove());
                clouds.removeAll(world.getCloudsToRemove());
                deadActors.removeAll(world.getDeadActorsToRemove());
                world.getBulletsToRemove().clear();
                world.getPowerUPSToRemove().clear();
                world.getEnemyBulletsToRemove().clear();
                world.getCloudsToRemove().clear();
                world.getDeadActorsToRemove().clear();

                world.cloudMaker();
                if (enemies.size() < 5) {
                    world.enemyMaker(5);
                }
                if(powerups.size() == 0 && player.isPlaying()){
                    if (new Random().nextInt(1000) % 997 == 0) {
                        world.powerUPMaker(1);
                    }
                }
                repaint();
            }
            if (player.getHealth() <= 0) {
                repaint();
            }
            if (player.isPaused()) {
                repaint();
            }
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //System.out.println(player.getHealth());
        }
    }
}
