package ru.nsu.fit.g14203.pospelov.filter.filters;


import ru.nsu.fit.g14203.pospelov.filter.filters.model.Filter;
import ru.nsu.fit.g14203.pospelov.filter.filters.model.FilterCore;
import  java.awt.*;
import java.awt.image.BufferedImage;

public class BlurFilter implements Filter {


    @Override
    public void apply(BufferedImage image, BufferedImage endImage, int...args) {
        FilterCore core = new FilterCore(6,new int[][]{{0,1,0},{1,2,1},{0,1,0}});
        int w = endImage.getWidth();
        int h = endImage.getHeight();
        //BufferedImage filteredImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < h; y ++){
            for(int x = 0; x < w; x++){
                endImage.setRGB(x,y,valueToColor(core.getFilteredPixel(x, y, image)).getRGB());
            }
        }
        //return filteredImage;
    }

    private Color valueToColor(int[] value){
        int red = clamp8bit(value[0]);
        int green = clamp8bit(value[1]);
        int blue = clamp8bit(value[2]);
        return new Color(red, green, blue);
    }

    private int clamp8bit(int colorValue){
        return Math.min(Math.max(colorValue, 0), 255);
    }

}
