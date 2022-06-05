package com.client.view;

import java.awt.Font;

import java.util.List;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.client.Client;
import com.client.view.themes.ThemesEnum;
import com.common.Message;
import com.common.publisher.ISubscriber;

public class Chat extends JTextPane implements ISubscriber
{
    private Client client;
    private StyledDocument styledDoc;
    private SimpleAttributeSet otherClientMessage;
    private SimpleAttributeSet clientMessage;
    private SimpleAttributeSet serverMessage;
    private ThemesEnum theme = ThemesEnum.DEFAULT;

    public Chat(Client client)
    {
        super();
        this.client = client;
        client.addSubscriber(this);
        this.setEditable(false);

        styledDoc = this.getStyledDocument();

        otherClientMessage = new SimpleAttributeSet();
        StyleConstants.setAlignment(otherClientMessage, StyleConstants.ALIGN_LEFT);
        StyleConstants.setFontSize(otherClientMessage, 14);

        clientMessage = new SimpleAttributeSet();
        StyleConstants.setAlignment(clientMessage, StyleConstants.ALIGN_RIGHT);
        StyleConstants.setFontSize(clientMessage, 14);

        serverMessage = new SimpleAttributeSet();
        StyleConstants.setAlignment(serverMessage, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontSize(serverMessage, 14);

        this.setTheme(theme);
        this.setFont(new Font("Calibri", Font.BOLD, 14));
    }

    public void setTheme(ThemesEnum theme)
    {
        StyleConstants.setForeground(otherClientMessage, theme.otherClientMessageColor);
        StyleConstants.setBackground(otherClientMessage, theme.otherClientBubbleColor);

        StyleConstants.setForeground(clientMessage, theme.clientMessageColor);
        StyleConstants.setBackground(clientMessage, theme.clientBubbleColor);
        
        StyleConstants.setForeground(serverMessage, theme.serverMessageColor);

        this.setBackground(theme.backgroundColor);
    }

    private void appendToChat(String chatMessage, SimpleAttributeSet attributeSet)
    {
        styledDoc.setParagraphAttributes(styledDoc.getLength(), 1, attributeSet, false);
        try 
        {
            styledDoc.insertString(styledDoc.getLength(), chatMessage, attributeSet);
        } 
        catch (BadLocationException e) 
        {
            e.printStackTrace();
        }
    }

    private String buildChatMessage(String nickname, String data)
    {
        if (nickname.equals(client.getNickname()))
        {
            return "<You>" + data;
        }
        return nickname + data;
    }

    @Override
    public void reactOnNotify() 
    {
        if (client.getSocket().isClosed())
        {
            return;
        }
        List<Message> chat = client.getChat();
        Message newMessage = chat.get(chat.size() - 1);

        String nickname = newMessage.getNickname();
        String data = newMessage.getData();
        String chatMessage = buildChatMessage(nickname, data);
        if (nickname.equals("server"))
        {
            appendToChat("\t*** " + data + " ***\n", serverMessage);
        }
        else if (nickname.equals(client.getNickname()))
        {
            appendToChat(chatMessage, clientMessage);
        }
        else
        {
            appendToChat(chatMessage, otherClientMessage);
        }
    }
}
