package com.Raiden.entities;
import com.Raiden.core.World;

import javax.swing.Timer;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class DeadActor {
    private int posX;
    private int posY;
    private int width, height;
    private Image img;

    public DeadActor(int posX, int posY, int width, int height, Image img, World world){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.img = img;
        ActionListener listener = new AutoRemove(this, world.getDeadActorsToRemove());
        Timer t =new Timer(500, listener);
        t.start();
    }

    public void draw(Graphics g, int[] sourcePoint) {
        Random random = new Random();
        int index = random.nextInt(5) * 4;
        g.drawImage(img, posX, posY, posX + width, posY + height,
                sourcePoint[index], sourcePoint[index + 1], sourcePoint[index + 2], sourcePoint[index + 3], null);
    }
}

class AutoRemove implements ActionListener {
    DeadActor deadActor;
    ArrayList<DeadActor> deadActorsToRemove;
    public AutoRemove(DeadActor deadActor, ArrayList<DeadActor> deadActorsToRemove){
        this.deadActor = deadActor;
        this.deadActorsToRemove = deadActorsToRemove;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        deadActorsToRemove.add(deadActor);
    }
}
