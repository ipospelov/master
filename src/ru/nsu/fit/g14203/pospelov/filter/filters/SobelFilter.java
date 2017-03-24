package ru.nsu.fit.g14203.pospelov.filter.filters;


import ru.nsu.fit.g14203.pospelov.filter.filters.model.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class SobelFilter implements Filter{
    private int border;
    private static int[][] core = {{1,2,1},{2,1,2},{1,2,1}};
    @Override
    public void apply(BufferedImage image, BufferedImage endImage, int... args) {
        Filter filter = new BWFilter();
        border = args[0];
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage oldImage = deepCopy(image);
        filter.apply(image, oldImage);

        for (int y = 0; y < h; y ++){
            for(int x = 0; x < w; x++){
                endImage.setRGB(x,y, getFilteredPixel(x, y, oldImage).getRGB());
            }
        }
    }

    private Color getFilteredPixel(int x, int y, BufferedImage image){
        int sx = 0;
        int sy = 0;
        for(int i = -1; i < 2; i++){
            sx += getComponent(x, y, i, 1, image) - getComponent(x, y, i, -1, image);
            sy += getComponent(x, y, 1, i, image) - getComponent(x, y, -1, i, image);
        }
        int s = Math.abs(sx) + Math.abs(sy);
        int newColor = 0;
        if(s > border)
            newColor = 255;

        return new Color(newColor, newColor, newColor);
    }

    private int getComponent(int x, int y, int i, int j, BufferedImage image) {
        if (x + i >= 0 && x + i < image.getWidth() && y + j >= 0 && y + j < image.getHeight()) {
            Color currentColor = new Color(image.getRGB(x + i, y + j));
            return core[i + 1][j + 1] * currentColor.getRed();
        } else
            return 0;
    }


    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

}
