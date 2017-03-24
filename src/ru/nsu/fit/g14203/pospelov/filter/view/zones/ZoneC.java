package ru.nsu.fit.g14203.pospelov.filter.view.zones;

import ru.nsu.fit.g14203.pospelov.filter.view.Zone;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ZoneC extends Zone {
    private BufferedImage canvas;
    private int zoneSideSize;
    private double scaleX, scaleY, maxScale;
    private int xToShow, yToShow;


    public ZoneC(int zoneSideSize){
        super(zoneSideSize);
        this.zoneSideSize = zoneSideSize;
        xToShow = 0;
        yToShow = 0;
        scaleX = 1;
        scaleY = 1;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, xToShow, yToShow, this);
    }

    public void setCanvas(BufferedImage image) {
        canvas = image;
        setScale();
        repaint();
    }

    public BufferedImage getCanvas() {
        return canvas;
    }

    public void setCanvas(File file) {
        try {
            canvas = ImageIO.read(file);
            setScale();
            xToShow = 0;
            yToShow = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        repaint();
    }

    private void setScale(){
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        scaleX =  (double) w / (double) zoneSideSize;
        scaleY =  (double) h / (double) zoneSideSize;
        maxScale = Math.max(scaleX, scaleY);
    }

    public void moveCanvas(int x, int y){
        xToShow = (int)(-x * maxScale);
        yToShow = (int)(-y * maxScale);
        repaint();
    }

    public void setCurrentImagePosition(Point p){
        xToShow = p.x;
        yToShow = p.y;
        repaint();
    }

    public void setScaleX2(){
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        scaleX = 2*(double) zoneSideSize / (double) w;
        scaleY = 2*(double) zoneSideSize / (double) h;
        maxScale = Math.max(scaleX, scaleY);
       // rectangleSize = (int)(zoneSideSize * maxScale);

        BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(maxScale, maxScale);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(canvas, after);
        canvas = after;
        xToShow -= zoneSideSize/4;
        yToShow -= zoneSideSize/4;
        repaint();
    }



}
