package ru.nsu.fit.g14203.pospelov.filter.controller;


import ru.nsu.fit.g14203.pospelov.filter.filters.model.FilterType;
import ru.nsu.fit.g14203.pospelov.filter.view.MainPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.concurrent.*;

public class Controller {



    private class ZoneBChanger implements Runnable{
        @Override
        public void run() {
            mainPanel.getZoneA().changeChangeMode(true);
            while (mouseDown) {
                try {
                    mainPanel.changeZonesBC();
                    mainPanel.getZoneA().repaint();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            mainPanel.getZoneA().changeChangeMode(false);
            mainPanel.getZoneA().repaint();
            mainPanel.copyBToC();
        }
    }

    private MainPanel mainPanel;
    private boolean isSelectMode;
    private boolean mouseDown;
    private boolean imageOpened;
    private ThreadPoolExecutor executorService;
    private ZoneBChanger zoneBChanger;
    private File lastOpened;

    public Controller(MainPanel mainPanel){

        //executorService = Executors.newSingleThreadExecutor();
        executorService = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10));
        this.mainPanel = mainPanel;
        isSelectMode = false;
        mouseDown = false;
        imageOpened = false;
        zoneBChanger = new ZoneBChanger();
        setListeners();

    }

    private void setListeners(){
        mainPanel.getZoneA().addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                if(isSelectMode) { //button on panel is chosen
                    mouseDown = true;
                    executorService.execute(zoneBChanger);

                }
            }

            public void mouseReleased(MouseEvent e) {
                mouseDown = false;
            }

        });
    }

    public void applyFilter(FilterType type, int ...args){
        if(imageOpened) {
            if(mainPanel.getZoneC().getCanvas() == null)
                mainPanel.copyBToC();
            executorService.getQueue().clear();
            executorService.execute(() -> mainPanel.applyFilter(type, args));
        }
    }

    public void applyNotModified(){
        mainPanel.applyNotModified();
    }

    public void setNotModified() {
        mainPanel.setNotModified();
    }

    public void openImage(File file){
        mainPanel.setNewImageToZone(file);
        lastOpened = file;
        imageOpened = true;
    }

    public void reOpen(){
        mainPanel.setNewImageToZone(lastOpened);
        mainPanel.getZoneC().setCurrentImagePosition(new Point(0,0));
        mainPanel.copyBToC();
    }

    public void changeSelectMode(){
        isSelectMode = !isSelectMode;
    }

    public Point getCurrentPosition(){
        return mainPanel.getCurrentImagePosition();
    }

    public void copyCToB(){
        mainPanel.copyCToB();
    }

    public void setScaleC(){
        if(mainPanel.getZoneC().getCanvas() != null)
            mainPanel.setScaleC();
    }

    public void saveImage(File file) {
        try {
            if(imageOpened)
                ImageIO.write(mainPanel.getZoneC().getCanvas(), "png",file);
        }catch (Exception e){
            System.out.println("Could not write to file");
        }
    }
}
