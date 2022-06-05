package com.common.publisher;

import java.util.concurrent.CopyOnWriteArrayList;

public class Publisher 
{
    private final CopyOnWriteArrayList<ISubscriber> subscribers = new CopyOnWriteArrayList<>();

    public void addSubscriber(ISubscriber subscriber)
    {
        subscribers.add(subscriber);
    }

    public void removeSubscriber(ISubscriber subscriber)
    {
        subscribers.remove(subscriber);
    }

    public void publishNotify()
    {
        for (ISubscriber subscriber : subscribers)
        {
            subscriber.reactOnNotify();
        }
    }
}
