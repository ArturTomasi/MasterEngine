
package com.mn.engcivil.interfaces.tables;

/**
 *
 * @author Matheus
 */
public abstract class AbstractColumn<T>
    implements 
        Column<T>
{
    private String label;

    public AbstractColumn( String label )
    {
        this.label = label;
    }

    @Override
    public String getLabel()
    {
        return label;
    }
}
