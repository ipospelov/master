package ru.nsu.fit.g14203.pospelov.filter.filters;

import ru.nsu.fit.g14203.pospelov.filter.filters.model.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Rotation implements Filter{
    private int alpha;
    public static int xb;
    public static int yb; //смещение по у
    private int x0, y0; //center
    private static int anglePerRadian = 57;
    @Override
    public void apply(BufferedImage image, BufferedImage endImage, int... args) {
        alpha = args[0];
        /*xb = args[1];
        yb = args[2];*/
        x0 = 175 + Math.abs(xb);
        y0 = 175 + Math.abs(yb);
        int w = image.getWidth();
        int h = image.getHeight();
        for (int y = 0; y < h; y ++){
            for(int x = 0; x < w; x++){
                endImage.setRGB(x,y, getFilteredPixel(x, y, image).getRGB());
            }
        }
    }

    private Color getFilteredPixel(int x, int y, BufferedImage image) {
        double len = Math.sqrt(Math.pow(x - x0, 2)+ Math.pow(y - y0, 2));
        //double horizontalAlpha = (x - x0)/(Math.sqrt(Math.pow(x - x0, 2) + Math.pow(y - y0,2)));
        double angle = (Math.atan2(y - y0, x - x0) + (double) alpha/ (double)anglePerRadian) ;
        int xn = (int)Math.round(x0 + len*Math.cos(angle));
        int yn = (int)Math.round(y0 + len*Math.sin(angle));
        if(xn >= 0 && xn < image.getWidth() && yn >= 0 && yn < image.getHeight())
            return new Color(image.getRGB(xn,yn));
        else
            return new Color(255,255,255);
    }
}
