package ru.nsu.fit.g14203.pospelov.filter.view;

import ru.nsu.fit.g14203.pospelov.filter.filters.model.Filter;
import ru.nsu.fit.g14203.pospelov.filter.filters.model.FilterType;
import ru.nsu.fit.g14203.pospelov.filter.model.FilterGetter;
import ru.nsu.fit.g14203.pospelov.filter.view.zones.ZoneA;
import ru.nsu.fit.g14203.pospelov.filter.view.zones.ZoneB;
import ru.nsu.fit.g14203.pospelov.filter.view.zones.ZoneC;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;

public class MainPanel extends JPanel{

    private ZoneA zoneA; //original image
    private ZoneB zoneB; //peace of image
    private ZoneC zoneC; //handled image
    private BufferedImage notModifiedC;

    private int borderSize;
    private int zoneSideSize;

    private FilterGetter filterGetter;

    public MainPanel(int zoneSideSize, int borderSize){

        this.zoneSideSize = zoneSideSize;
        this.borderSize = borderSize;
        filterGetter = new FilterGetter();

        setPreferredSize(new Dimension(4 * borderSize + 3 * zoneSideSize, 2 * borderSize + zoneSideSize));

        setLayout(null);

        zoneA = new ZoneA(zoneSideSize);
        zoneB = new ZoneB(zoneSideSize);
        zoneC = new ZoneC(zoneSideSize);

        setZonesLocation();

        add(zoneA);
        add(zoneB);
        add(zoneC);


    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private void setZonesLocation(){
        zoneA.setBounds(borderSize, borderSize, zoneSideSize, zoneSideSize);
        zoneB.setBounds(2 * borderSize + zoneSideSize, borderSize, zoneSideSize, zoneSideSize);
        zoneC.setBounds(3 * borderSize + 2 * zoneSideSize, borderSize, zoneSideSize, zoneSideSize);
    }

    public void setNewImageToZone(File image){
        zoneA.setCanvas(image);
        zoneB.setCanvas(image);
    }

    public void changeZonesBC() {
        zoneB.moveCanvas(zoneA.getXbPosition(), zoneA.getYbPosition());

        //zoneC.moveCanvas(zoneA.getXbPosition(), zoneA.getYbPosition());
    }

    public void applyFilter(FilterType type, int[] args){
        Filter filter = filterGetter.getFilter(type);
        //zoneC.setCanvas(filter.apply(zoneB.getCanvas()));
        //if(type == FilterType.FLOYD_DITHERING)
        filter.apply(zoneB.getCanvas(), zoneC.getCanvas(), args);
        //else
        //    filter.apply(zoneB.getCanvas(), zoneC.getCanvas());
        zoneC.repaint();
    }

    public ZoneA getZoneA() {
        return zoneA;
    }

    public ZoneB getZoneB() {
        return zoneB;
    }

    public ZoneC getZoneC() {
        return zoneC;
    }

    public void applyNotModified(){
        if(notModifiedC != null) {
            copyImageState(notModifiedC, zoneC.getCanvas());
            zoneC.repaint();
        }
        //zoneC.setCanvas(zoneB.getCanvas());
    }

    public void setNotModified(){
        if(notModifiedC != null)
            copyImageState(zoneC.getCanvas(), notModifiedC);
    }

    private void copyImageState(BufferedImage from, BufferedImage to){
        for(int y = 0; y < from.getHeight(); y++)
            for (int x = 0; x < from.getWidth(); x++) {
                to.setRGB(x, y, from.getRGB(x, y));
            }
    }

    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public Point getCurrentImagePosition(){
        return zoneB.getCurrentImagePosition();
    }

    public void copyBToC(){
        if(zoneC.getCanvas() == null) {
            zoneC.setCanvas(deepCopy(zoneB.getCanvas()));
            notModifiedC = deepCopy(zoneC.getCanvas());
        }
        else
            copyImageState(zoneB.getCanvas(),zoneC.getCanvas());
        zoneC.setCurrentImagePosition(zoneB.getCurrentImagePosition());
    }

    public void copyCToB(){
        if(zoneC.getCanvas() != null){
            copyImageState(zoneC.getCanvas(),zoneB.getCanvas());
            zoneB.repaint();
        }
    }

    public void setScaleC(){
        zoneC.setScaleX2();
    }

}
