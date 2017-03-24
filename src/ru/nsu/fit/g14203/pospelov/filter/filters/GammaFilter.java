package ru.nsu.fit.g14203.pospelov.filter.filters;

import ru.nsu.fit.g14203.pospelov.filter.filters.model.Filter;
import ru.nsu.fit.g14203.pospelov.filter.view.MainPanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GammaFilter implements Filter{
    private int gamma;
    private static double gammaMax = 200;
    @Override
    public void apply(BufferedImage image, BufferedImage endImage, int... args) {
        gamma = args[0];
        int w = image.getWidth();
        int h = image.getHeight();

        for (int y = 0; y < h; y ++){
            for(int x = 0; x < w; x++){
                endImage.setRGB(x,y, getFilteredPixel(x, y, image).getRGB());
            }
        }
    }

    private Color getFilteredPixel(int x, int y, BufferedImage image) {
        Color oldColor = new Color(image.getRGB(x, y));
        return new Color(getChangedValue(oldColor.getRed()), getChangedValue(oldColor.getGreen()), getChangedValue(oldColor.getBlue()));
    }

    private int getChangedValue(int color){
        return clamp8bit((int)(Math.pow((double)color / 255, (double)gamma/gammaMax) * 255));
    }

    private int clamp8bit(int colorValue){
        return Math.min(Math.max(colorValue, 0), 255);
    }
}
