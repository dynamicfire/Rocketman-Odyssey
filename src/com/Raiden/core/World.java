package com.Raiden.core;

import com.Raiden.entities.*;
import com.Raiden.graphics.*;
import com.Raiden.audio.SoundEffect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static java.awt.event.KeyEvent.*;

/**
 * Game's world initializer: add flights, bullets, power-ups etc.
*/
public class World implements Runnable, KeyListener, ActionListener, MouseListener, MouseMotionListener {
    public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    public ArrayList<EnemyBullet> enemyBullets = new ArrayList<EnemyBullet>();
    public ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
    public ArrayList<EnemyBullet> enemyBulletsToRemove = new ArrayList<EnemyBullet>();
    public ArrayList<PowerUP> powerUPS = new ArrayList<PowerUP>();
    public ArrayList<PowerUP> powerUPSToRemove = new ArrayList<PowerUP>();
    public ArrayList<Enemy> enemy = new ArrayList<Enemy>();
    public ArrayList<Enemy> enemiesToRemove = new ArrayList<Enemy>();
    public ArrayList<Cloud> clouds = new ArrayList<Cloud>();
    public ArrayList<Cloud> cloudsToRemove = new ArrayList<Cloud>();
    public ArrayList<Background> backgrounds = new ArrayList<Background>();
    public ArrayList<DeadActor> deadActors = new ArrayList<DeadActor>();
    public ArrayList<DeadActor> deadActorsToRemove = new ArrayList<DeadActor>();
    public Player player;
    public Background background1, background2;
    public JFrame frame;
    public Image powerUPImg;
    public Image bulletImg;
    public Image enemyBulletImg;
    public Image backgroundImg1, backgroundImg2;
    public Image playerImg;
    public Image enemyImg;
    public Image deadImg;
    public Image cloudImg;


    public World(JFrame frame){
        this.frame = frame;
        try{
            imageLoad();
        } catch (IOException e) {
            e.printStackTrace();
        }
        background1 = new Background(0, 550, 800, backgroundImg1, frame);
        background2 = new Background(1, 550, 800, backgroundImg2, frame);
        backgrounds.add(background1);
        backgrounds.add(background2);
        powerUPS.add(new Shield(32, 32, powerUPImg, frame, this));
        player = new Player(frame.getWidth()/2, frame.getHeight() + 60, 15, 15, 1000, 25, 60, playerImg, frame, this);
        enemyMaker(10);
    }

    private void imageLoad() throws IOException {
        enemyImg = new ImageIcon(this.getClass().getClassLoader().getResource("images/enemy-big.png")).getImage();
        playerImg = new ImageIcon(this.getClass().getClassLoader().getResource("images/ship.png")).getImage();
        powerUPImg = new ImageIcon(this.getClass().getClassLoader().getResource("images/power-up.png")).getImage();
        enemyBulletImg = new ImageIcon(this.getClass().getClassLoader().getResource("images/enemy-bullet.png")).getImage();
        bulletImg = new ImageIcon(this.getClass().getClassLoader().getResource("images/laser-bolts.png")).getImage();
        backgroundImg1 = new ImageIcon(this.getClass().getClassLoader().getResource("images/desert-background.png")).getImage();
        backgroundImg2 = new ImageIcon(this.getClass().getClassLoader().getResource("images/desert-background2.png")).getImage();
        cloudImg = new ImageIcon(this.getClass().getClassLoader().getResource("images/clouds-transparent.png")).getImage();
        deadImg = new ImageIcon(this.getClass().getClassLoader().getResource("images/explosion.png")).getImage();
    }

    public void enemyMaker(int MAX){
        int numOfEnemy = new Random().nextInt(MAX + 1);
        for(int i = 0; i < numOfEnemy; i++){
            float enemyPosX = new Random().nextInt(frame.getWidth());
            enemy.add(new Enemy(enemyPosX,-54, 300, 50, 54, enemyImg, 10, frame, this));
        }
    }

    public void powerUPMaker(int MAX){
        int numOfpowerups = new Random().nextInt(MAX + 1);
        for(int i = 0; i < numOfpowerups; i++){
            if(new Random().nextBoolean()) {
                powerUPS.add(new Shield(32, 32, powerUPImg, frame, this));
            }
            else
                powerUPS.add(new Laser(32, 32, powerUPImg, frame, this));
        }
    }

    public void cloudMaker(){
        //System.out.println(clouds.size());
        if(clouds.size() <= 2) {
            if (new Random().nextInt(10) % 7 == 0){
                clouds.add(new Cloud(cloudImg, frame));
            }
        }
    }

    public ArrayList<PowerUP> getPowerUPS(){return powerUPS;}
    public ArrayList<Bullet> getBullet() {return bullets;}
    public ArrayList<EnemyBullet> getEnemyBullets() {return enemyBullets;}
    public ArrayList<Enemy> getEnemy() {return enemy;}
    public Player getPlayer() {return player;}
    public ArrayList<Bullet> getBulletsToRemove(){return bulletsToRemove;}
    public ArrayList<PowerUP> getPowerUPSToRemove() {return powerUPSToRemove;}
    public ArrayList<Enemy> getEnemiesToRemove() {return enemiesToRemove;}
    public ArrayList<EnemyBullet> getEnemyBulletsToRemove() {return enemyBulletsToRemove;}
    public ArrayList<Background> getBackground(){return backgrounds;}
    public ArrayList<Cloud> getClouds() {return clouds;}
    public ArrayList<Cloud> getCloudsToRemove() {return cloudsToRemove;}
    public ArrayList<DeadActor> getDeadActorsToRemove() {return deadActorsToRemove;}
    public ArrayList<DeadActor> getDeadActors() {return deadActors;}

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode())
        {
            case VK_ENTER :
                if(player.getHealth() > 0 && !player.isPaused() && player.isPlaying() && new Random().nextBoolean())player.fireAt(this);
                break;
            case VK_F2 :
                if(player.getHealth() <= 0 && player.isPlaying()){
                    player.setScore(0);
                    player.setHealth(1000);
                    bullets.clear();
                }
                break;
            case VK_SPACE :
                if(player.getHealth() > 0 && player.isPlaying()){
                    player.setPaused(!player.isPaused());
                }
                break;
            case VK_F1 :
                if(!player.isPlaying()){
                    player.setPlaying(true);
                    player.setPosY(frame.getHeight() - 80);
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void run() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //powerUPS.add(new Shield(32, 32, powerUPImg, frame));
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(player.isPlaying() && !player.isPaused() && player.getHealth() > 0) {
            player.setPosX(e.getX());
            player.setPosY(e.getY());
            player.boardDetection();
        }

    }


}
