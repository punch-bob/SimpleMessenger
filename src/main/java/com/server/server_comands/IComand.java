package com.server.server_comands;

import com.server.ClientListener;

public interface IComand 
{
    public void execute(ClientListener client);
}
