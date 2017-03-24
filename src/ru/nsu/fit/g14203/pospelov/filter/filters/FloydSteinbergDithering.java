package ru.nsu.fit.g14203.pospelov.filter.filters;


import ru.nsu.fit.g14203.pospelov.filter.filters.model.Filter;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

//TODO: parameters view
public class FloydSteinbergDithering implements Filter{
    private int dnr, dng, dnb;
    @Override
    public void apply(BufferedImage image, BufferedImage endImage, int... args) {
        dnr = /*255/*/(args[0] - 1);
        dng = /*255/*/(args[1] - 1);
        dnb = /*255/*/(args[2] - 1);
        BufferedImage newImage = deepCopy(image);
        for(int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++) {
                setDitheredPixelColor(x, y, newImage);
            }
        copyState(newImage, endImage);
        //endImage = deepCopy(newImage);
    }

    private void setDitheredPixelColor(int x, int y, BufferedImage image){
        int red, green, blue;
        int rError, gError, bError;
        Color currentColor = new Color(image.getRGB(x,y));
        red = getNewColor(currentColor.getRed(), dnr);//Math.round((float) currentColor.getRed()/(float)dnr) * dnr;
        green = getNewColor(currentColor.getGreen(), dng);//Math.round((float) currentColor.getGreen()/(float)dnr) * dnr;
        blue = getNewColor(currentColor.getBlue(), dnb);//Math.round((float) currentColor.getBlue()/(float)dnr) * dnr;
        rError = currentColor.getRed() - red;
        gError = currentColor.getGreen() - green;
        bError = currentColor.getBlue() - blue;

        image.setRGB(x, y, new Color(red, green, blue).getRGB());

        addError(x + 1, y, image, (7.0/16.0)*rError, (7./16.)*gError, (7./16.)*bError);
        addError(x - 1, y + 1, image, (3./16.)*rError, (3./16.)*gError, (3./16.)*bError);
        addError(x, y + 1, image, (5./16.)*rError, (5./16.)*gError, (5./16.)*bError);
        addError(x + 1, y + 1, image, (1./16.)*rError, (1./16.)*gError, (1./16.)*bError);

    }

    private void addError(int x, int y, BufferedImage image, double rError, double gError, double bError){
        if(x >= 0 && x < image.getWidth() && y >= 0 && y<image.getHeight()){
            Color colorToSet = new Color(image.getRGB(x, y));
            image.setRGB(x,y, new Color(clamp8bit(colorToSet.getRed() + (int)rError),
                    clamp8bit(colorToSet.getGreen() + (int)gError),
                    clamp8bit(colorToSet.getBlue() + (int)bError)).getRGB());
        }
    }

    private int getNewColor(int color, int n){
        double dn = 255/(double)(n);
        return (int)Math.round(Math.round((double) color/dn) * dn);
    }

    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    private void copyState(BufferedImage from, BufferedImage to){
        for(int y = 0; y < from.getHeight(); y++)
            for (int x = 0; x < from.getWidth(); x++) {
                to.setRGB(x, y, from.getRGB(x, y));
            }
    }
    private int clamp8bit(int colorValue){
        return Math.min(Math.max(colorValue, 0), 255);
    }
}
