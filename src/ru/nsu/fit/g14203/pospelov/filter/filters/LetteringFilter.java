package ru.nsu.fit.g14203.pospelov.filter.filters;

import ru.nsu.fit.g14203.pospelov.filter.filters.model.Filter;
import ru.nsu.fit.g14203.pospelov.filter.filters.model.FilterCore;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LetteringFilter implements Filter{


    @Override
    public void apply(BufferedImage image, BufferedImage endImage, int...args) {
        FilterCore core = new FilterCore(1,new int[][]{{0, 1, 0},{-1, 0, 1},{0,-1,0}});
        int w = image.getWidth();
        int h = image.getHeight();
        //BufferedImage filteredImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < h; y ++){
            for(int x = 0; x < w; x++){
                endImage.setRGB(x,y,valueToColor(core.getFilteredPixel(x, y, image)).getRGB());
            }
        }
        //return filteredImage;
    }

    private Color valueToColor(int[] value){
        int red = clamp8bit(value[0] + 128);
        int green = clamp8bit(value[1] + 128);
        int blue = clamp8bit(value[2] + 128);
        return new Color(red, green, blue);
    }

    private int clamp8bit(int colorValue){
        return Math.min(Math.max(colorValue, 0), 255);
    }
}
