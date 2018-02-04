package com.me.eng.ui.tables;

/**
 *
 * @author Matheus
 */
public interface Column<T>
{
    default public String getWidth()
    {
        return "min";
    }

    public String getLabel();
    
    public abstract Object getValueAt( T value );
}
