package com.client.view;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


import com.client.Client;

public class InputPanel extends JPanel
{
    private String initialInscription = "Write a message...";

    public InputPanel(Client client)
    {
        super();
        SendButton sendButton = new SendButton();
        JTextField messageField = new JTextField(initialInscription);
        messageField.setForeground(Color.GRAY);
        messageField.setColumns(30);
        messageField.setFont(new Font("Calibri", Font.BOLD, 14));
        this.setLayout(new FlowLayout());
        this.add(messageField);
        this.add(sendButton);

        messageField.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                messageField.setForeground(Color.BLACK);
                messageField.setText(null);
            }
        });

        messageField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e) 
            {
                if (e.getKeyChar() == '\n') 
                {
                    String message = messageField.getText();
                    if (message != null)
                    {
                        client.sendMessage(message);
                        messageField.setForeground(Color.GRAY);
                        messageField.setText(initialInscription);
                        messageField.setFocusable(false);
                        messageField.setFocusable(true);
                    }
                }
            }
        });

        sendButton.addActionListener(e ->
        {
            String message = messageField.getText();
            if (message != null)
            {
                client.sendMessage(message);
                messageField.setForeground(Color.GRAY);
                messageField.setText(initialInscription);
                messageField.setFocusable(false);
                messageField.setFocusable(true);
            }
        });
    }
}
