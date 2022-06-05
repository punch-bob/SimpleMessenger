package com.server.server_comands;

import com.server.ClientListener;

public enum ServerComands implements IComand
{
    STOP("stop", "leave the chat")
    {
        @Override
        public void execute(ClientListener client) 
        {
            client.closeConnection();
        }
    },

    GETCLIENTSLIST("get clients list", "view the list of users")
    {
        @Override
        public void execute(ClientListener client) 
        {
            client.printClientsList();
        }
    },

    HELP("help", null)
    {
        @Override
        public void execute(ClientListener client) 
        {
            client.printHelpMessage();
        }
    };

    private String comandName;
    private String description;

    private ServerComands(String comandName, String description)
    {
        this.comandName = comandName;
        this.description = description;
    }

    public String getComandName()
    {
        return comandName;
    }

    public String getDescription()
    {
        return description;
    }
}
