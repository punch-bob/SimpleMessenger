package com.client.view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import com.client.Client;
import com.common.publisher.ISubscriber;

public class MainDrawer extends JFrame implements ISubscriber
{
    private Client client;

    public MainDrawer(Client client)
    {
        super("SIMPLE MESSENGER");
        iconSetting();

        this.client = client;
        client.addSubscriber(this);

        String nickname = JOptionPane.showInputDialog("Enter your nickname");
        client.setNickname(nickname);
        client.sendMessage(nickname);

        Chat chat = new Chat(client);
        JScrollPane scrollPane = new JScrollPane(chat);
        InputPanel inputPanel = new InputPanel(client);
        ThemeSelection themeSelection = new ThemeSelection(chat);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(themeSelection);

        this.setJMenuBar(menuBar);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(inputPanel, BorderLayout.SOUTH);
        this.addWindowListener(new CloseWindowListener(client));

        this.setFocusable(true);
        this.setSize(500, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void iconSetting()
    {
        ImageIcon icon = new ImageIcon("src/main/resources/messenger_icon.png");
        this.setIconImage(icon.getImage());
    }

    @Override
    public void reactOnNotify() 
    {
        if (client.getSocket().isClosed())
        {
            this.dispose(); 
        }
    }
}
