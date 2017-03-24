package ru.nsu.fit.g14203.pospelov.filter.filters;

import ru.nsu.fit.g14203.pospelov.filter.filters.model.Filter;

import java.awt.Color;

import java.awt.image.BufferedImage;

public class BWFilter implements Filter {
    private static double redWeight = 0.299;
    private static double greenWeight = 0.587;
    private static double blueWeight = 0.114;


    @Override
    public void apply(BufferedImage image, BufferedImage endImage, int...args) {
        int w = image.getWidth();
        int h = image.getHeight();
        //BufferedImage bwImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Color  currentColor;
        int average;
        for (int y = 0; y < h; y ++){
            for(int x = 0; x < w; x++){
                    currentColor = new Color(image.getRGB(x,y));
                    average = (int)(currentColor.getRed() * redWeight +
                            currentColor.getGreen() * greenWeight +
                            currentColor.getBlue() * blueWeight);
                    endImage.setRGB(x,y, new Color(average, average, average).getRGB());
            }
        }
        //return bwImage;
    }
}
