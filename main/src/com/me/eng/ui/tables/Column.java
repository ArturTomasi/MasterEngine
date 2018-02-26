/* 
 *  Filename:    Column 
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
public interface Column<T>
{
    /**
     * String
     * 
     * @return default
     * @ignored getWidth
     */
    default public String getWidth()
    {
        return "min";
    }

    /**
     * getLabel
     * 
     * @return String
     */
    public String getLabel();
    
    /**
     * Object
     * 
     * @param value T
     * @return abstract
     * @ignored getValueAt
     */
    public abstract Object getValueAt( T value );
}
