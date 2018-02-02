package com.mn.engcivil.interfaces.tables;

import org.zkoss.zul.Listcell;

/**
 *
 * @author Matheus
 */
public interface TableCellRenderer<T>
{
    public void render( T value, Column column, Listcell cell );
}
