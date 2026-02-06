package com.Raiden.graphics;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Cloud {
    private int posX;
    private int posY;
    private int speedY;
    private int width;
    private int height; //image size
    private Image img;
    private JFrame frame;

    public Cloud(Image img, JFrame frame){
        Random speedGenerator = new Random();
        int speed = speedGenerator.nextInt(10) + 1;
        this.posX = 0;
        this.posY = -222;
        this.speedY = speed;
        this.width = 550;
        this.height = 222;
        this.img = img;
        this.frame = frame;
    }

    public void move(ArrayList<Cloud> cloudsToRemove){
        if(posY >= frame.getHeight()){
            cloudsToRemove.add(this);
        }
        posY += speedY;
    }

    public void draw(Graphics g, int[] sourcePoint) {
        g.drawImage(img,
                posX, posY,
                posX + width,
                posY + height,
                sourcePoint[0], sourcePoint[1], sourcePoint[2], sourcePoint[3], null);
    }
}
