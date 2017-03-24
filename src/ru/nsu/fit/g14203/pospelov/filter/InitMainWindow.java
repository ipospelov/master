package ru.nsu.fit.g14203.pospelov.filter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.*;

import ru.nsu.cg.MainFrame;
import ru.nsu.fit.g14203.pospelov.filter.controller.Controller;
import ru.nsu.fit.g14203.pospelov.filter.filters.Rotation;
import ru.nsu.fit.g14203.pospelov.filter.filters.model.FilterType;
import ru.nsu.fit.g14203.pospelov.filter.view.MainPanel;
import ru.nsu.fit.g14203.pospelov.filter.view.SettingsDialog;

/**
 * Main window class
 * @author Ivan Pospelov
 */
public class InitMainWindow extends MainFrame {


	/**
	 * Default constructor to create main window
	 */
    private JScrollPane scrollPane;
    private Controller controller;
	private static int windowWidth = 1120;
	private static int windowHeight = 470;

	private InitMainWindow()
	{
		super(windowWidth, windowHeight, "Init application");
		MainPanel mainPanel = new MainPanel(350, 15);

		controller = new Controller(mainPanel);

		scrollPane = new JScrollPane(mainPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);

/*		JPanel test = new SettingsDialog("first", "second");
		int result = JOptionPane.showConfirmDialog(null, test,
				"Exit without saving", JOptionPane.OK_CANCEL_OPTION);*/

		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		try
		{
			addSubMenu("File", KeyEvent.VK_F);
			addSubMenu("Edit", KeyEvent.VK_F);
			addSubMenu("Edit/Filters", KeyEvent.VK_F2);

			addMenuItem("File/Exit", "Exit application", KeyEvent.VK_X, "exit.png", "onExit");
			addMenuItem("File/Save as...", "Save file", KeyEvent.VK_S, "save.png", "onSave");
			addMenuItem("File/Open", "Open file", KeyEvent.VK_O, "open.png", "onOpen");
			addMenuItem("File/About...", "Shows program version and copyright information", KeyEvent.VK_A, "About.gif", "onAbout");

			addMenuItem("Edit/Select", "Used to select a fragment", KeyEvent.VK_X, "select.png", "onSelect");
			addMenuItem("Edit/C to B", "Replace image from Zone C to Zone B", KeyEvent.VK_R, "replace.png", "onReplace");
			addMenuItem("Edit/Zoom x2", "Zoom", KeyEvent.VK_Z, "zoom.png", "onZoom");
			addMenuItem("Edit/New document", "Disable changes", KeyEvent.VK_Z, "trash.png", "onTrash");


			addMenuItem("Edit/Filters/To B&W", "Turn to black and white", KeyEvent.VK_B, "bw.png", "onBW");
			addMenuItem("Edit/Filters/To negative", "Turn to negative", KeyEvent.VK_N, "negative.png", "onNegative");
			addMenuItem("Edit/Filters/Blur", "Apply blur filter", KeyEvent.VK_N, "blur.png", "onBlur");
			addMenuItem("Edit/Filters/Sharp", "Apply sharp filter", KeyEvent.VK_N, "sharp.png", "onSharp");
			addMenuItem("Edit/Filters/Lettering", "Letter image", KeyEvent.VK_L, "lettering.png", "onLettering");
			addMenuItem("Edit/Filters/Watercolor", "Apply effect of watercolor image",
					KeyEvent.VK_W, "watercolor.png", "onWatercolor");
			addMenuItem("Edit/Filters/Order dithering", "Setting color pallet smaller", KeyEvent.VK_N, "dithering.png", "onDithering");
			addMenuItem("Edit/Filters/Floyd dithering", "Setting color pallet smaller", KeyEvent.VK_F, "dithering.png", "onFloyd");
			addMenuItem("Edit/Filters/Roberts", "Border accentuation", KeyEvent.VK_B, "border.png", "onRoberts");
			addMenuItem("Edit/Filters/Sobel", "Border accentuation", KeyEvent.VK_B, "border.png", "onSobel");
			addMenuItem("Edit/Filters/Gamma", "Gamma converting", KeyEvent.VK_G, "gamma.png", "onGamma");
			addMenuItem("Edit/Filters/Rotation", "Rotate image", KeyEvent.VK_R, "rotation.png", "onRotate");



			addToolBarButton("File/Open");
			addToolBarSeparator();
			addToolBarButton("File/Save as...");
			addToolBarSeparator();
			addToolBarButton("File/Exit");
			addToolBarSeparator();
			addToolBarButton("File/About...");
			addToolBarSeparator();
			addToolBarButton("Edit/New document");
			addToolBarSeparator();
			addToolBarButton("Edit/Select");
			addToolBarSeparator();
			addToolBarButton("Edit/C to B");
			addToolBarSeparator();
			addToolBarButton("Edit/Zoom x2");
			addToolBarSeparator();
			addToolBarButton("Edit/Filters/To B&W");
			addToolBarSeparator();
			addToolBarButton("Edit/Filters/To negative");
			addToolBarSeparator();
			addToolBarButton("Edit/Filters/Blur");
			addToolBarSeparator();
			addToolBarButton("Edit/Filters/Sharp");
			addToolBarSeparator();
			addToolBarButton("Edit/Filters/Lettering");
			addToolBarSeparator();
			addToolBarButton("Edit/Filters/Watercolor");
			addToolBarSeparator();
			addToolBarButton("Edit/Filters/Order dithering");
			addToolBarSeparator();
			addToolBarButton("Edit/Filters/Floyd dithering");
			addToolBarSeparator();
			addToolBarButton("Edit/Filters/Roberts");
			addToolBarSeparator();
			addToolBarButton("Edit/Filters/Sobel");
			addToolBarSeparator();
			addToolBarButton("Edit/Filters/Gamma");
			addToolBarSeparator();
			addToolBarButton("Edit/Filters/Rotation");



		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}

	}


	public void onTrash(){
		controller.reOpen();
	}

	public void onExit(){
		System.exit(0);
	}

	public void onOpen(){
		JFileChooser chooser = new JFileChooser();
		File currentDirFile = new File("");
		String path = currentDirFile.getAbsolutePath() + "/filter/FIT_14203_Pospelov_Ivan_Filter_Data";
		File f = new File(path);
		if(!f.exists()){
			path = currentDirFile.getAbsolutePath() + "/FIT_14203_Pospelov_Ivan_Filter_Data";
			f = new File(path);
		}
		chooser.setCurrentDirectory(f);
		int ret = chooser.showDialog(this, "Открыть файл");
		if(ret == JFileChooser.CANCEL_OPTION)
			return;
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			controller.openImage(file);
		}
	}

	public void onSave(){
		JFileChooser chooser = new JFileChooser();
		File currentDirFile = new File("");
		String path = currentDirFile.getAbsolutePath() + "/filter/FIT_14203_Pospelov_Ivan_Filter_Data";
		File f = new File(path);
		if(!f.exists()){
			path = currentDirFile.getAbsolutePath() + "/FIT_14203_Pospelov_Ivan_Filter_Data";
			f = new File(path);
		}
		chooser.setCurrentDirectory(f);
		int ret = chooser.showSaveDialog(this);
		if(ret == JFileChooser.CANCEL_OPTION)
			return;
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = new File(chooser.getSelectedFile()+".png");
			//File file = chooser.getSelectedFile();
			controller.saveImage(file);
		}
	}

	public void onAbout(){
		JOptionPane.showMessageDialog(this, "2016 Ivan Pospelov, FIT, group 14203", "About Init", JOptionPane.INFORMATION_MESSAGE);
	}

	public void onSelect(){
		controller.changeSelectMode();
	}

	public void onBW(){
		controller.applyFilter(FilterType.BLACK_AND_WHITE);
	}

	public void onNegative(){
		controller.applyFilter(FilterType.NEGATIVE);
	}

	public void onBlur(){
		controller.applyFilter(FilterType.BLUR);
	}

	public void onSharp(){
		controller.applyFilter(FilterType.SHARP);
	}

	public void onLettering(){
		controller.applyFilter(FilterType.LETTERING);
	}

	public void onWatercolor(){
		controller.applyFilter(FilterType.WATERCOLOR);
	}

	public void onDithering(){
		controller.applyFilter(FilterType.ORDER_DITHERING);
	}

	public void onFloyd() {
		SettingsDialog settings = new SettingsDialog("Size of red palette:", "Size of green palette:", "Size of blue palette:");
		initSettings(settings, FilterType.FLOYD_DITHERING);
	}

	public void onRoberts(){
		SettingsDialog settings = new SettingsDialog("Border value:");
		settings.setMinimum(0);
		settings.setMaximum(500);
		settings.setValues(30);
		initSettings(settings, FilterType.ROBERTS);
	}

	public void onSobel(){
		SettingsDialog settings = new SettingsDialog("Border value:");
		settings.setMinimum(0);
		settings.setMaximum(500);
		settings.setValues(100);
		initSettings(settings, FilterType.SOBEL);
	}

	public void onGamma(){
		SettingsDialog settings = new SettingsDialog("Gamma * 200 value:");
		settings.setMinimum(10);
		settings.setMaximum(500);
		settings.setValues(200);
		initSettings(settings, FilterType.GAMMA);
	}

	public void onRotate(){
		Rotation.xb = controller.getCurrentPosition().x;
		Rotation.yb = controller.getCurrentPosition().y;
		SettingsDialog settings = new SettingsDialog("Rotation angle:");
		settings.setMinimum(-180);
		settings.setMaximum(180);
		settings.setValues(0);
		initSettings(settings, FilterType.ROTATION);
	}

	public void onReplace(){
		controller.copyCToB();
	}

	public void onZoom(){
		controller.setScaleC();
	}



	private void initSettings(SettingsDialog settings, FilterType filterType){
		controller.setNotModified();
		settings.setActionOnChange(filterType, controller);
		settings.apply(filterType, controller);
		int result = JOptionPane.showConfirmDialog(this, settings,
				"Converting settings", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.CANCEL_OPTION) {
			controller.applyNotModified();
		}
		if (result == JOptionPane.OK_OPTION){
			settings.apply(filterType, controller);
		}
	}



	/**
	 * Application main entry point
	 * @param args command line arguments (unused)
	 */
	public static void main(String[] args)
	{
		InitMainWindow mainFrame = new InitMainWindow();
		mainFrame.setVisible(true);
	}
}
