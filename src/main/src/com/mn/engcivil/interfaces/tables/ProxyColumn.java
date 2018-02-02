package com.mn.engcivil.interfaces.tables;

/**
 *
 * @author Matheus
 */
public class ProxyColumn<T>
    implements 
        Column<T>
{
    private Column<T> column;

    public ProxyColumn( Column<T> column )
    {
        this.column = column;
    }
    
    @Override
    public String getLabel()
    {
        return column.getLabel();
    }

    @Override
    public String getWidth()
    {
        return column.getWidth();
    }
    
    @Override
    public Object getValueAt( T value )
    {
        return column.getValueAt( value );
    }
}
