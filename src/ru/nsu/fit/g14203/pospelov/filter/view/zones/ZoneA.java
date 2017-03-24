package ru.nsu.fit.g14203.pospelov.filter.view.zones;

import ru.nsu.fit.g14203.pospelov.filter.view.Zone;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ZoneA extends Zone{

    private BufferedImage canvas;
    private int zoneSideSize;
    private boolean bChangeMode;
    private double scaleX, scaleY, minScale;
    private int xbPosition, ybPosition;
    private int rectangleSize;


    public ZoneA(int zoneSideSize){
        super(zoneSideSize);
        this.zoneSideSize = zoneSideSize;
        bChangeMode = false;
        scaleX = 0;
        scaleY = 0;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, this);
        if(bChangeMode){
            g.setColor(Color.WHITE);
            g.setXORMode(Color.BLACK);
            drawRectangle(g);
            //rectangleDrawer.drawRectangle(g);
        }
    }

    private void drawRectangle(Graphics g){
        int x0, x1, y0, y1;

        Point p = MouseInfo.getPointerInfo().getLocation();
        Point panelCoord = this.getLocation();
        SwingUtilities.convertPointToScreen(panelCoord, this);

        int rightBorder = Math.min((int)(panelCoord.x +  zoneSideSize * minScale / scaleX), panelCoord.x + zoneSideSize);
        int bottomBorder = Math.min((int)(panelCoord.y + zoneSideSize * minScale / scaleY), panelCoord.y + zoneSideSize);

        if(scaleY >= 1 || scaleX >= 1){
            xbPosition = 0;
            ybPosition = 0;
            drawRectangle(0, 0, zoneSideSize, zoneSideSize, g);
            return;
        }

        if(p.x  <= panelCoord.x + rectangleSize/2) {
            x0 = 0;
            x1 = rectangleSize;
        }else if(p.x + rectangleSize/2 >= rightBorder) {
            x1 = rightBorder - panelCoord.x;
            x0 = x1 - rectangleSize;
        }else {
            x1 = p.x - panelCoord.x + rectangleSize / 2;
            x0 = p.x - panelCoord.x - rectangleSize / 2;
        }

        if(p.y  <= panelCoord.y + rectangleSize/2) {
            y0 = 0;
            y1 = rectangleSize;
        }else if(p.y + rectangleSize/2 >= bottomBorder) {
            y1 = bottomBorder - panelCoord.y;
            y0 = y1 - rectangleSize;
        }else {
            y1 = p.y - panelCoord.y + rectangleSize / 2;
            y0 = p.y - panelCoord.y - rectangleSize / 2;
        }

        drawRectangle(x0, y0, x1, y1, g);


        xbPosition = x0;
        ybPosition = y0;
    }

    private void drawRectangle(int x0, int y0, int x1, int y1, Graphics g){
        g.drawLine(x1, y1, x0, y1);
        g.drawLine(x0, y1, x0, y0);
        g.drawLine(x1, y0, x0, y0);
        g.drawLine(x1, y1, x1, y0);
    }

    public void changeChangeMode(boolean newValue){
        bChangeMode = newValue;
    }

    public void setCanvas(File file){
        try {
            canvas = ImageIO.read(file);
            setScaledCanvas();
        } catch (IOException e) {
            e.printStackTrace();
        }
        repaint();
    }

    public int getXbPosition() {
        return xbPosition;
    }

    public int getYbPosition() {
        return ybPosition;
    }

    private void setScaledCanvas(){
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        scaleX = (double) zoneSideSize / (double) w;
        scaleY = (double) zoneSideSize / (double) h;
        minScale = Math.min(scaleX, scaleY);
        rectangleSize = (int)(zoneSideSize * minScale);

        BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(minScale, minScale);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(canvas, after);
        canvas = after;
    }
}
