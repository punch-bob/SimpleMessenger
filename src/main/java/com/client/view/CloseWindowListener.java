package com.client.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.client.Client;

public class CloseWindowListener extends WindowAdapter
{
    private Client client;

    public CloseWindowListener(Client client)
    {
        this.client = client;
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        super.windowClosing(e);
        client.sendMessage("\\stop");
    }
}
