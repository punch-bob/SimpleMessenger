package com.server;

import java.util.LinkedList;
import java.util.List;

import com.common.Message;

public class ChatHistory 
{
    private LinkedList<Message> history;
    private int numberOfSavedMessages;

    public ChatHistory(int numberOfSavedMessages)
    {
        this.numberOfSavedMessages = numberOfSavedMessages;
        history = new LinkedList<>();
    }

    public synchronized void addMessage(Message message)
    {
        if (history.size() >= numberOfSavedMessages)
        {
            history.removeFirst();
            history.add(message);
        }
        else
        {
            history.add(message);
        }
    }

    public List<Message> getHistory()
    {
        return history;
    }
}
