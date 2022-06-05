package com.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurator 
{
    private boolean isLogging;
    private int numberOfSavedMessages;
    private int maxChatClients;
    private int timeout;
    private String dateFormat;

    public Configurator()
    {
        Properties properties = new Properties();
        try
        {
            InputStream in = Configurator.class.getClassLoader().getResourceAsStream("configuration.properties");
            properties.load(in);
        }
        catch (NullPointerException | IOException e)
        {
            e.printStackTrace();
        }

        isLogging = Boolean.parseBoolean(properties.getProperty("isLogging"));
        numberOfSavedMessages = Integer.parseInt(properties.getProperty("NumberOfSavedMessage"));
        maxChatClients = Integer.parseInt(properties.getProperty("MaxChatClients"));
        timeout = Integer.parseInt(properties.getProperty("Timeout"));
        dateFormat = properties.getProperty("DateFormat");
    }

    public boolean isLogging()
    {
        return isLogging;
    }

    public int getNumberOfSavedMessages()
    {
        return numberOfSavedMessages;
    }

    public int getMaxChatClients()
    {
        return maxChatClients;
    }

    public int getTimeout()
    {
        return timeout;
    }

    public String getDateFormat()
    {
        return dateFormat;
    }
}
