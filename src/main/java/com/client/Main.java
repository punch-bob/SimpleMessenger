package com.client;

import com.client.view.MainDrawer;

public class Main 
{
    public static void main(String[] args) 
    {
        Client client = new Client(8080, "localhost");
        MainDrawer chatFrame = new MainDrawer(client);
        chatFrame.setVisible(true);
        new Thread(client).start();
    }
}