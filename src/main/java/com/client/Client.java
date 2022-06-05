package com.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.common.Configurator;
import com.common.Message;
import com.common.publisher.Publisher;

public class Client extends Publisher implements Runnable
{
    private List<Message> chat;
    private String nickname;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private SimpleDateFormat dateFormat;
    private Configurator configurator;
    private Socket socket;

    public Client(int port, String hostname)
    {
        try 
        {
            socket = new Socket(hostname, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        configurator = new Configurator();
        dateFormat = new SimpleDateFormat(configurator.getDateFormat());
        chat = new ArrayList<>();
    }

    public void  sendMessage(String message)
    {
        try
        {
            Message chatMessage;
            if (message.trim().charAt(0) != '\\')
            {
                Date currentTime = new Date();
                String formatedTime = dateFormat.format(currentTime);
                chatMessage = new Message(nickname, message + " (" + formatedTime + ")\n");
            }
            else
            {
                chatMessage = new Message(nickname,  message.trim());
            }
            outputStream.writeObject(chatMessage);
            outputStream.flush();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public SimpleDateFormat getDateFormat()
    {
        return dateFormat;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = "<" + nickname + ">";
    }

    public List<Message> getChat()
    {
        return chat;
    }

    public void closeConnection() 
    {
        try
        {
            if (!socket.isClosed())
            {
                socket.close();
                inputStream.close();
                outputStream.close();
                publishNotify();
            }
        }
        catch (IOException e) {} 
    }

    public Socket getSocket()
    {
        return socket;
    }

    @Override
    public void run() 
    {
        while (true)
        {
            try 
            {
                Message message = (Message)inputStream.readObject();
                if (message != null)
                {
                    if (message.getData().substring(1).equals("stop"))
                    {
                        break;
                    }
                    chat.add(message);
                    publishNotify();
                }
            } 
            catch (IOException | ClassNotFoundException e) {}
        }
        closeConnection(); 
    }
}
