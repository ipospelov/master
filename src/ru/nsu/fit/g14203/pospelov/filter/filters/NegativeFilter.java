package ru.nsu.fit.g14203.pospelov.filter.filters;

import ru.nsu.fit.g14203.pospelov.filter.filters.model.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NegativeFilter implements Filter {
    static private int maxValue = 255;
    @Override
    public void apply(BufferedImage image, BufferedImage endImage, int...args) {
        int w = image.getWidth();
        int h = image.getHeight();
        //BufferedImage bwImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Color currentColor;
        for (int y = 0; y < h; y ++){
            for(int x = 0; x < w; x++){
                currentColor = new Color(image.getRGB(x,y));
                endImage.setRGB(x,y, new Color(maxValue - currentColor.getRed(),
                        maxValue - currentColor.getGreen(),
                        maxValue - currentColor.getBlue()).getRGB());
            }
        }
        //return bwImage;
    }
}
