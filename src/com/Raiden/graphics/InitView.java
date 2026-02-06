package com.Raiden.graphics;

import com.Raiden.audio.SoundEffect;
import com.Raiden.core.World;

import javax.swing.*;

public class InitView {
    public static void main(String args[]) {
        System.out.println("Hello");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame win = new JFrame();
                win.setSize(550,800);
                win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                win.setVisible(true);
                win.setTitle("Rocketman's Odyssey");
                World world = new World(win);
                DrawCanvas DC = new DrawCanvas(win, world);
                win.add(DC);
                win.addKeyListener(world);
                win.addMouseMotionListener(world);
                win.setResizable(false);
                Thread t1 = new Thread(DC);
                t1.start();
                //SoundEffect.init();
                SoundEffect BGM = new SoundEffect("sound/meet-the-princess.wav");
                Thread t2 = new Thread(BGM);
                //BGM.playBackGround("sound/An Endless Desert (main gameplay music).wav");
                t2.start();
                //BGM.disposeSound();

            }
        });

    }
}
