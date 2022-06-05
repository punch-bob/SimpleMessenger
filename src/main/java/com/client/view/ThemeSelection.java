package com.client.view;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButton;

import java.awt.event.ItemEvent;

import com.client.view.themes.ThemesEnum;

public class ThemeSelection extends JMenu
{
    public ThemeSelection(Chat chat)
    {
        super("Themes");
        ButtonGroup buttonGroup = new ButtonGroup();
        for(ThemesEnum theme : ThemesEnum.values())
        {
            String themeName = theme.name().replaceAll("_", " ");
            JRadioButton themeRadioButton = new JRadioButton(themeName, theme.themeImage);
            if (themeName.equals("DEFAULT"))
            {
                themeRadioButton.setSelected(true);
            }
            
            themeRadioButton.addItemListener((e) -> 
            {
                if (e.getStateChange() == ItemEvent.SELECTED) 
                {
                    theme.apply(chat);
                }
                });
            this.add(themeRadioButton);
            buttonGroup.add(themeRadioButton);
        }
    }
}
