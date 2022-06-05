package com.client.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Image;

public class SendButton extends JButton
{
    public SendButton()
    {
        super();
        ImageIcon icon = new ImageIcon("src/main/resources/send_button.png");
        Image image = icon.getImage();  
        Image newImage = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH); 
        icon = new ImageIcon(newImage); 
        this.setIcon(icon);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
    }
}
