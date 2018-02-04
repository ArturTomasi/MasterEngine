package com.me.eng.ui;

/**
 *
 * @author Matheus
 */
public class Callback<T>
{
    private T source;

    public Callback()
    {
    }
    
    public Callback( T source )
    {
        this.source = source;
    }
    
    public void setSource( T source )
    {
        this.source = source;
    }

    public T getSource()
    {
        return source;
    }

    public void acceptInput() throws Exception
    {
    }

    public void cancelInput() throws Exception
    {
    }
    
    public void rejectInput() throws Exception
    {
    }
}
