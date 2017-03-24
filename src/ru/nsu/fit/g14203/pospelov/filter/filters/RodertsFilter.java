package ru.nsu.fit.g14203.pospelov.filter.filters;

import javafx.scene.paint.*;
import ru.nsu.fit.g14203.pospelov.filter.filters.model.Filter;

import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class RodertsFilter implements Filter{
    private int border;
    @Override
    public void apply(BufferedImage image, BufferedImage endImage, int... args) {
        Filter filter = new BWFilter();
        border = args[0];
        int w = image.getWidth();
        int h = image.getHeight();
        //BufferedImage bwImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        BufferedImage oldImage = deepCopy(image);
        filter.apply(image, oldImage);

        Color currentColor;
        for (int y = 0; y < h; y ++){
            for(int x = 0; x < w; x++){
                endImage.setRGB(x,y, getFilteredPixel(x, y, oldImage).getRGB());
            }
        }
    }

    private Color getFilteredPixel(int x, int y, BufferedImage image){
        Color grad = getGrad(x, y, image);
        int red = 0, green = 0, blue = 0;
        if(grad.getRed() > border)
            red = 255;
        if(grad.getGreen() > border)
            green = 255;
        if(grad.getBlue() > border)
            blue = 255;
        return new Color(red, green, blue);
    }

    private Color getGrad(int x, int y, BufferedImage image){
        int w = image.getWidth();
        int h = image.getHeight();
        Color pixelBR;
        Color pixelTR;
        Color pixelBL;
        Color pixelTL = new Color(image.getRGB(x,y));
        if(x + 1 < w && y + 1 < h){
            pixelBR = new Color(image.getRGB(x + 1, y + 1));
        }else {
            pixelBR = new Color(0,0,0);
        }

        if(x + 1 < w){
            pixelTR = new Color(image.getRGB(x + 1, y));
        }else {
            pixelTR = new Color(0,0,0);
        }

        if(y + 1 < h){
            pixelBL = new Color(image.getRGB(x, y + 1));
        }else {
            pixelBL = new Color(0,0,0);
        }

        int red = clamp8bit(Math.abs(pixelTL.getRed() - pixelBR.getRed()) + Math.abs(pixelTR.getRed() - pixelBL.getRed()));
        int green = clamp8bit(Math.abs(pixelTL.getGreen() - pixelBR.getGreen()) + Math.abs(pixelTR.getGreen() - pixelBL.getGreen()));
        int blue = clamp8bit(Math.abs(pixelTL.getBlue() - pixelBR.getBlue()) + Math.abs(pixelTR.getBlue() - pixelBL.getBlue()));
        return new Color(red, green, blue);

    }

    private int clamp8bit(int colorValue){
        return Math.min(Math.max(colorValue, 0), 255);
    }

    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
