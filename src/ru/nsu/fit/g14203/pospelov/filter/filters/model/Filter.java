package ru.nsu.fit.g14203.pospelov.filter.filters.model;


import java.awt.image.BufferedImage;

public interface Filter{
    void apply(BufferedImage image, BufferedImage endImage, int...args);
}
