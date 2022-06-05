package com.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.common.Message;
import com.server.server_comands.ServerComands;

public class ClientListener extends Thread
{
    private Server server;
    private Socket socket;
    private ObjectInputStream inSocketStream;
    private ObjectOutputStream outSocketStream;
    private String clientNickname;


    public ClientListener(Socket socket, Server server) throws IOException
    {
        this.server = server;
        this.socket = socket;
        outSocketStream  = new ObjectOutputStream(socket.getOutputStream());
        inSocketStream = new ObjectInputStream(socket.getInputStream());
        socket.setSoTimeout(1000);
        this.start();
    }

    private void setClientNickname()
    {
        Message clientInitMessage = null;
        while (clientInitMessage == null)
        {
            try 
            {
                clientInitMessage = (Message)inSocketStream.readObject();
            }
            catch (IOException | ClassNotFoundException e) 
            {
                clientInitMessage = null;
            }
        }
        this.clientNickname = clientInitMessage.getNickname();
    }

    public void closeConnection()
    {
        send(new Message(clientNickname, "\\stop"));
        server.removeClient(this);
        try
        {
            if (!socket.isClosed())
            {
                socket.close();
                inSocketStream.close();
                outSocketStream.close();
            }
        }
        catch (IOException e) {}
    }

    public void send(Message message)
    {
        try 
        {
            outSocketStream.writeObject(message);
            outSocketStream.flush();
        } 
        catch (IOException e) {}
    }

    public void printClientsList()
    {
        send(new Message("server", "Clients: "));
        for (ClientListener client : server.getClients())
        {
            if (client.clientNickname.equals(this.clientNickname))
            {
                send(new Message("server", "<You>"));
            }
            else
            {
                send(new Message("server", client.clientNickname));
            }
        }
    }

    public void printHelpMessage()
    {
        send(new Message("server", "Chat Features: "));
        for (ServerComands comand : ServerComands.values())
        {
            String comandName = comand.getComandName();
            String description = comand.getDescription();

            if (comandName.equals("help"))
            {
                continue;
            }
            send(new Message("server", "print \\" + comandName + " for " + description));
        }
    }

    private String getComand(String comandLine)
    {
        return comandLine.trim().substring(1).toUpperCase().replaceAll("\\s+", "");
    }

    private void executeServerComand(String data)
    {
        String comandName = getComand(data);
        ServerComands comand = ServerComands.valueOf(comandName);
        comand.execute(this);
    }

    private boolean isServerComand(String data)
    {
        if (data.trim().charAt(0) == '\\')
        {
            String comandName = getComand(data);
            try
            {
                Enum.valueOf(ServerComands.class, comandName);
                return true;
            }
            catch (IllegalArgumentException e)
            {
                return false;
            }
        }
        return false;
    }

    @Override
    public void run()
    {
        server.printChatHistory(outSocketStream);
        setClientNickname();
        Message connectionMessage = new Message("server", "the user " + clientNickname + " connected to the chat");
        server.addMessageToHistory(connectionMessage);
        server.sendMessageToAll(connectionMessage);

        Message message = null;
        long start = System.currentTimeMillis();
        while (true)
        {
            try 
            {
                message = (Message)inSocketStream.readObject();
            }
            catch (IOException | ClassNotFoundException e) 
            {
                message = null;
            }

            if (message != null)
            {
                start = System.currentTimeMillis();
                String data = message.getData();
                server.infoLog("User: " + message.getNickname() + "\nSend text: " + data);
                if (isServerComand(data))
                {
                    executeServerComand(data);
                    if (socket.isClosed())
                    {
                        Message disconnectionMessage = new Message("server", "the user " + clientNickname + " has disconnected");
                        server.addMessageToHistory(disconnectionMessage);
                        server.sendMessageToAll(disconnectionMessage);
                        break;
                    }
                }
                else
                {
                    server.addMessageToHistory(message);
                    server.sendMessageToAll(message);
                }
            }

            if (System.currentTimeMillis() - start >= server.getTimeout())
            {
                Message inactivityMessage = new Message("server", "the user " + clientNickname + " was disconnected due to inactivity");
                server.addMessageToHistory(inactivityMessage);
                server.sendMessageToAll(inactivityMessage);
                closeConnection();
                break;
            }
        } 
    }
}
