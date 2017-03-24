package ru.nsu.fit.g14203.pospelov.filter.filters;


import ru.nsu.fit.g14203.pospelov.filter.filters.model.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.Arrays;

public class WaterColorFilter implements Filter{

    @Override
    public void apply(BufferedImage image, BufferedImage endImage, int...args) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage filteredImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < h; y ++){
            for(int x = 0; x < w; x++){
                filteredImage.setRGB(x,y,getMedianColor(x, y, image).getRGB());
            }
        }
        SharpFilter sharpFilter = new SharpFilter();
        sharpFilter.apply(filteredImage, endImage);
    }

    private Color getMedianColor(int x, int y, BufferedImage image){
        int[][] areaColors = new int[3][25];
        Arrays.fill(areaColors[0], 0);
        Arrays.fill(areaColors[1], 0);
        Arrays.fill(areaColors[2], 0);

        int rightBorder = clamp(x + 2, image.getWidth());
        int leftBorder = clamp(x - 2, image.getWidth());
        int topBorder = clamp(y - 2, image.getHeight());
        int bottomBorder = clamp(y + 2, image.getHeight());
        int k = 0;

        for(int i = topBorder; i < bottomBorder; i++){
            for(int j = leftBorder; j < rightBorder; j++){
                Color currentColor = new Color(image.getRGB(j, i));
                areaColors[0][k++] = currentColor.getRed();
                areaColors[1][k] = currentColor.getGreen();
                areaColors[2][k] = currentColor.getBlue();
            }
        }
        Arrays.sort(areaColors[0]);
        Arrays.sort(areaColors[1]);
        Arrays.sort(areaColors[2]);
        int meaningfulValues = (rightBorder - leftBorder) * (bottomBorder - topBorder);
        int meaningfulIndex = meaningfulValues / 2 + meaningfulValues % 2 + areaColors[0].length - meaningfulValues - 1;
        return new Color(areaColors[0][meaningfulIndex], areaColors[1][meaningfulIndex], areaColors[2][meaningfulIndex]);
    }

    private int clamp(int value, int max){
        return Math.min(Math.max(value, 0), max);
    }

}
