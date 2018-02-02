package com.mn.engcivil.interfaces.tables;

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
