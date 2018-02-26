/* 
 *  Filename:    ProxyColumn 
 *
 *  Author:      Artur Tomasi
 *  EMail:       tomasi.artur@gmail.com
 *  Internet:    www.masterengine.com.br
 *
 *  Copyright © 2018 by Over Line Ltda.
 *  95900-038, LAJEADO, RS
 *  BRAZIL
 *
 *  The copyright to the computer program(s) herein
 *  is the property of Over Line Ltda., Brazil.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda.
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.ui.tables;

/**
 *
 * @author Matheus
 * @param <T>
 */
public class ProxyColumn<T>
    implements 
        Column<T>
{
    private Column<T> column;

    /**
     * ProxyColumn
     * 
     * @param column Column&lt;T&gt;
     */
    public ProxyColumn( Column<T> column )
    {
        this.column = column;
    }
    
    /**
     * getLabel
     * 
     * @return String
     */
    @Override
    public String getLabel()
    {
        return column.getLabel();
    }

    /**
     * getWidth
     * 
     * @return String
     */
    @Override
    public String getWidth()
    {
        return column.getWidth();
    }
    
    /**
     * getValueAt
     * 
     * @param value T
     * @return Object
     */
    @Override
    public Object getValueAt( T value )
    {
        return column.getValueAt( value );
    }
}
