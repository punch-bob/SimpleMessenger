package com.server.logger;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.server.Main;

public class ServerLogger
{
    private final Logger log;

    public ServerLogger()
    {
        try 
        {
            LogManager.getLogManager().readConfiguration(Main.class.getClassLoader().getResourceAsStream("logging.properties"));
        } 
        catch (SecurityException | IOException e) 
        {
            e.printStackTrace();
        }
        
        this.log = Logger.getLogger(Logger.class.getName());
    }

    public void getInfoMessage(String message)
    {
        log.log(Level.INFO, "\n" + message + "\n");
    }

    public void getExceptionMessage(Level lvl, Throwable e)
    {
        log.log(lvl, "Exception: ", e);
    }
}
