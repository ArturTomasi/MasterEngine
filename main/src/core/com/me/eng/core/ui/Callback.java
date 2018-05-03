/* 
 *  Filename:    Callback 
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
package com.me.eng.core.ui;

/**
 *
 * @author Matheus
 * @param <T>
 */
public class Callback<T>
{
    private T source;

    /**
     * Callback
     * 
     */
    public Callback()
    {
    }
    
    /**
     * Callback
     * 
     * @param source T
     */
    public Callback( T source )
    {
        this.source = source;
    }
    
    /**
     * setSource
     * 
     * @param source T
     */
    public void setSource( T source )
    {
        this.source = source;
    }

    /**
     * getSource
     * 
     * @return T
     */
    public T getSource()
    {
        return source;
    }

    /**
     * acceptInput
     * 
     * @throws Exception
     */
    public void acceptInput() throws Exception {}

    /**
     * cancelInput
     * 
     * @throws Exception
     */
    public void cancelInput() throws Exception {}
    
    /**
     * rejectInput
     * 
     * @throws Exception
     */
    public void rejectInput() throws Exception {}
}
