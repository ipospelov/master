package ru.nsu.fit.g14203.pospelov.filter.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Zone extends JPanel{

    private BufferedImage canvas;
    private int zoneSideSize;

    public Zone(int zoneSideSize){

        this.zoneSideSize = zoneSideSize;
        initZone();

    }

    private void initZone(){
        setBackground(Color.WHITE);
        setOpaque(true);
        setPreferredSize(new Dimension(zoneSideSize, zoneSideSize));
        //setBorder(BorderFactory.createLineBorder(Color.black));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Graphics2D graphics2D = (Graphics2D) g;
        g.drawImage(canvas, 0, 0, this);
    }

}
