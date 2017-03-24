package ru.nsu.fit.g14203.pospelov.filter.view;

import ru.nsu.fit.g14203.pospelov.filter.controller.Controller;
import ru.nsu.fit.g14203.pospelov.filter.filters.model.FilterType;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.*;
import java.util.List;

public class SettingsDialog extends JPanel{

    private List<JSlider> sliders;
    private List<JTextField> textFields;
    private List<JButton> upButtons;
    private List<JButton> downButtons;


    public SettingsDialog(String ...fields){
        sliders = new ArrayList<>();
        textFields = new ArrayList<>();
        upButtons = new ArrayList<>();
        downButtons = new ArrayList<>();
        JLabel newLabel;
        JTextField newField;
        JSlider newSlider;
        JButton newUpButton, newDownButton;
        setLayout(new GridLayout(fields.length,5));
        for(int i = 0; i < fields.length; i++){
            newDownButton = new JButton("<");
            newUpButton = new JButton(">");
            newDownButton.setMargin(new java.awt.Insets(1, 2, 1, 2));
            newUpButton.setMargin(new java.awt.Insets(1, 2, 1, 2));
            upButtons.add(i, newUpButton);
            downButtons.add(i, newDownButton);
            newLabel = new JLabel(fields[i]);
            add(newLabel);
            newField = new JTextField(3);
            newField.setText("255");
            add(newField);
            textFields.add(i,newField);
            newSlider = new JSlider(2,255,255);
            add(newDownButton);
            add(newSlider);
            add(newUpButton);
            sliders.add(i, newSlider);

        }
        setListeners();
    }

    public void setMinimum(int ...args){
        for(int i = 0; i < sliders.size(); i++) {
            sliders.get(i).setMinimum(args[i]);
        }
    }

    public void setMaximum(int ...args){
        for(int i = 0; i < sliders.size(); i++) {
            sliders.get(i).setMaximum(args[i]);
        }
    }

    public void setValues(int ...values){
        for(int i = 0; i < sliders.size(); i++) {
            sliders.get(i).setValue(values[i]);
        }
    }

    public JSlider getSlider(int index){
        return sliders.get(index);
    }

    public JTextField getField(int index){
        return textFields.get(index);
    }

    private int getValueByIndex(int i){
        return sliders.get(i).getValue();
    }

    public int getNumberOfParams(){
        return sliders.size();
    }

    private void setListeners(){
        JSlider currentSlider;
        JTextField currentField;
        JButton upButton, downButton;
        for(int i = 0; i < sliders.size(); i++){
            final int j = i;
            currentSlider = sliders.get(i);
            currentField = textFields.get(i);
            upButton = upButtons.get(i);
            downButton = downButtons.get(i);
            currentField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    sliders.get(j).setValue(Integer.parseInt(textFields.get(j).getText()));
                }

                @Override
                public void focusLost(FocusEvent e) {
                    sliders.get(j).setValue(Integer.parseInt(textFields.get(j).getText()));
                }
            });
            currentSlider.addChangeListener(e -> textFields.get(j).setText(String.valueOf(sliders.get(j).getValue())));
            upButton.addActionListener(e -> sliders.get(j).setValue(sliders.get(j).getValue() + 1));
            downButton.addActionListener(e -> sliders.get(j).setValue(sliders.get(j).getValue() - 1));
        }
    }

    public void setActionOnChange(FilterType filterType, Controller controller){
        for(int i = 0; i < sliders.size(); i++){
            sliders.get(i).addChangeListener(e -> {
                int[] args = new int[sliders.size()];/*{getValueByIndex(0), getValueByIndex(1), getValueByIndex(2)};*/
                for(int i1 = 0; i1 < args.length; i1++){
                    args[i1] = getValueByIndex(i1);
                }
                controller.applyFilter(filterType, args);
            });
        }
    }

    public void apply(FilterType filterType,Controller controller){
        int[] args = new int[sliders.size()];
        for(int i1 = 0; i1 < args.length; i1++){
            args[i1] = Integer.valueOf(textFields.get(i1).getText());
        }
        controller.applyFilter(filterType, args);
    }
}
