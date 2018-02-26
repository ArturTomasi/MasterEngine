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
