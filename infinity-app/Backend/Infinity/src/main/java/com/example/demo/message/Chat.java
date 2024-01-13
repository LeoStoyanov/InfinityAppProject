package com.example.demo.message;

public class Chat
{
    private String senderName;
    private String reciverName;
    private String message;
    private String date;
    private Status status;

    public void setSenderName(String senderName)
    {
        this.senderName = senderName;
    }

    public void setReciverName(String reciverName)
    {
        this.reciverName = reciverName;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    public String getSenderName()
    {
        return senderName;
    }

    public String getReciverName()
    {
        return reciverName;
    }

    public String getMessage()
    {
        return message;
    }

    public String getDate()
    {
        return date;
    }

    public Status getStatus()
    {
        return status;
    }
}

