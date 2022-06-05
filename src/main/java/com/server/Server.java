package com.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import com.common.Configurator;
import com.common.Message;
import com.server.logger.ServerLogger;

public class Server implements Runnable
{
    private ServerSocket server;
    private SessionManager manager;
    private ChatHistory history;
    private ServerLogger logger;
    private boolean isLogging;
    private int timeout;

    public Server(int port)
    {
        logger = new ServerLogger();
        Configurator configurator = new Configurator();
        this.isLogging = configurator.isLogging();
        this.timeout = configurator.getTimeout();
        manager = new SessionManager();
        history = new ChatHistory(configurator.getNumberOfSavedMessages());
        try 
        {
            server = new ServerSocket(port, configurator.getMaxChatClients());
            infoLog("Server started!");
        } 
        catch (IOException e) 
        {
            exceptionLog(Level.WARNING, e);
        }
    }

    public Set<ClientListener> getClients()
    {
        return manager.getClients();
    }

    public void addClient(ClientListener client)
    {
        manager.addClient(client);
        infoLog("A new client has joined!");
    }

    public void removeClient(ClientListener client)
    {
        manager.removeClient(client);
        infoLog("The client disconnected!");
    }

    public void addMessageToHistory(Message message)
    {
        history.addMessage(message);
    }

    public void sendMessageToAll(Message message)
    {
        for (ClientListener client : manager.getClients())
        {
            client.send(message);
        }
    }

    public void printChatHistory(ObjectOutputStream writer)
    {
        List<Message> chatHistory = history.getHistory();
        if (chatHistory.size() > 0)
        {
            try
            {
                for(Message oldMessage : chatHistory)
                {
                    writer.writeObject(oldMessage); 
                }
                writer.flush();
            }
            catch (IOException e) 
            {
                exceptionLog(Level.WARNING, e);
            }
            
        }
    }

    public int getTimeout()
    {
        return timeout;
    }

    public void exceptionLog(Level lvl, Throwable e)
    {
        if (isLogging)
        {
            logger.getExceptionMessage(lvl, e);
        }
    }

    public void infoLog(String message)
    {
        if (isLogging)
        {
            logger.getInfoMessage(message);
        }
    }

    @Override
    public void run() 
    {
        infoLog("The server is running!");
        try
        {
            while (true)
            {
                Socket socket = server.accept();
                addClient(new ClientListener(socket, this));
            }
        } 
        catch (IOException e) 
        {
            exceptionLog(Level.WARNING, e);
        }
        finally
        {
            try 
            {
                server.close();
            } 
            catch (IOException e) 
            {
                exceptionLog(Level.WARNING, e);
            }
            infoLog("The server is disabled!");
        }
    }
}
