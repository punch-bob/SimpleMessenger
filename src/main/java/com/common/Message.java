package com.common;

import java.io.Serializable;

public class Message implements Serializable
{
    private String nickname;
    private String data;

    public Message(String nickname, String message)
    {
        this.nickname = nickname;
        this.data = message;
    }

    public String getNickname()
    {
        return nickname;
    }

    public String getData()
    {
        return data;
    }
}
