package com.client.view.themes;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

import com.client.view.Chat;

public enum ThemesEnum implements ThemeApplyer
{
    DEFAULT(0x8A2BE2, 0x4B0082,
            0xFFFFFF, 0xFFFFFF,
            0x4B0082, 0xFFFFFF,
            "src/main/resources/default_theme.png")
    {
        @Override
        public void apply(Chat chat)
        {
            chat.setTheme(ThemesEnum.DEFAULT);
        }
    },

    PINK(0xD21DAE, 0x98187E,
         0xFFFFFF, 0xFFFFFF,
         0xF0AFD6, 0xF7E7F5,
         "src/main/resources/pink_theme.png")
    {
        @Override
        public void apply(Chat chat)
        {
            chat.setTheme(ThemesEnum.PINK);
        }
    },

    DARK(0xA9A9A9, 0x696969,
         0xFFFFFF, 0xFFFFFF,
         0x696969, 0x000000,
         "src/main/resources/dark_theme.png")
    {
        @Override
        public void apply(Chat chat)
        {
            chat.setTheme(ThemesEnum.DARK);
        }
    },
           
    BLUE(0xB0D8FC, 0x66B1F2,
         0x071E36, 0x071E36,
         0x20A8B7, 0x071E36,
         "src/main/resources/blue_theme.png")
    {
        @Override
        public void apply(Chat chat)
        {
            chat.setTheme(ThemesEnum.BLUE);
        }
    },

    DARK_GREEN(0x7ECD23, 0x23CD5C,
               0xFFFFFF, 0xFFFFFF,
               0x3FA226, 0x0E1B08,
               "src/main/resources/dark_green_theme.png")
    {
        @Override
        public void apply(Chat chat)
        {
            chat.setTheme(ThemesEnum.DARK_GREEN);
        }
    },
           
    LIGHT_GREEN(0x6EA96C, 0x648163,
                0xFFFFFF, 0xFFFFFF,
                0x79D176, 0xE9FCE8,
                "src/main/resources/light_green_theme.png")
    {
        @Override
        public void apply(Chat chat)
        {
            chat.setTheme(ThemesEnum.LIGHT_GREEN);
        }
    },
           
    YELLOW(0xF9EB8D, 0xF6E261,
           0x5E4209, 0x5E4209,
           0xF6CF80, 0xFFFDE9,
           "src/main/resources/yellow_theme.png")
    {
        @Override
        public void apply(Chat chat)
        {
            chat.setTheme(ThemesEnum.YELLOW);
        }
    },
           
    FIERY(0xFFD64F, 0xD8622F,
          0x5E3A21, 0x5E3A21,
          0xB00404, 0x000000,
          "src/main/resources/fiery_theme.png")
    {
        @Override
        public void apply(Chat chat)
        {
            chat.setTheme(ThemesEnum.FIERY);
        }
    },
           
    WATER(0x6A79D7, 0x36AC7D,
          0xE1FFFE, 0xE1FFFE,
          0x99CCFF, 0xE1FFFE,
          "src/main/resources/water_theme.png")
    {
        @Override
        public void apply(Chat chat)
        {
            chat.setTheme(ThemesEnum.WATER);
        }
    };

    public Color clientBubbleColor;
    public Color otherClientBubbleColor;
    public Color clientMessageColor;
    public Color otherClientMessageColor;
    public Color serverMessageColor;
    public Color backgroundColor;
    public ImageIcon themeImage;

    private ThemesEnum(int clientBubbleColor, int otherClientBubbleColor, int clientMessageColor, int otherClientMessageColor, int serverMessageColor, int backgroundColor, String imagePath)
    {
        this.clientBubbleColor = new Color(clientBubbleColor);
        this.otherClientBubbleColor = new Color(otherClientBubbleColor);
        this.clientMessageColor = new Color(clientMessageColor);
        this.otherClientMessageColor = new Color(otherClientMessageColor);
        this.serverMessageColor = new Color(serverMessageColor);
        this.backgroundColor = new Color(backgroundColor);
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage();  
        Image newImage = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH); 
        this.themeImage =  new ImageIcon(newImage); 
    }
}
