package ru.nsu.fit.g14203.pospelov.filter.model;

import ru.nsu.fit.g14203.pospelov.filter.filters.*;
import ru.nsu.fit.g14203.pospelov.filter.filters.model.Filter;
import ru.nsu.fit.g14203.pospelov.filter.filters.model.FilterType;

import java.util.HashMap;
import java.util.Map;

public class FilterGetter {
    private Map<FilterType, Filter> filtersList;

    public FilterGetter(){

        filtersList = new HashMap<>();
        filtersList.put(FilterType.BLACK_AND_WHITE, new BWFilter());
        filtersList.put(FilterType.NEGATIVE, new NegativeFilter());
        filtersList.put(FilterType.BLUR, new BlurFilter());
        filtersList.put(FilterType.SHARP, new SharpFilter());
        filtersList.put(FilterType.LETTERING, new LetteringFilter());
        filtersList.put(FilterType.WATERCOLOR, new WaterColorFilter());
        filtersList.put(FilterType.ORDER_DITHERING, new OrderDithering());
        filtersList.put(FilterType.FLOYD_DITHERING, new FloydSteinbergDithering());
        filtersList.put(FilterType.ROBERTS, new RodertsFilter());
        filtersList.put(FilterType.SOBEL, new SobelFilter());
        filtersList.put(FilterType.GAMMA, new GammaFilter());
        filtersList.put(FilterType.ROTATION, new Rotation());



    }

    public Filter getFilter(FilterType type){
        return filtersList.get(type);
    }
}
