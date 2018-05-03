/* 
 *  Filename:    ActionColumn 
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
package com.me.eng.core.ui.tables;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Toolbarbutton;

/**
 *
 * @author Matheus
 */
public abstract class ActionColumn<T>
    implements 
        Column<T>
{
    private String label;

    /**
     * ActionColumn
     * 
     * @param label String
     */
    public ActionColumn( String label )
    {
        this.label = label;
    }
    
    /**
     * getLabel
     * 
     * @return String
     */
    @Override
    public String getLabel()
    {
        return "#";
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
        Toolbarbutton bt = new Toolbarbutton( label );

        bt.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                execute( value );
            }
        } );

        return bt;
    }
    
    /**
     * void
     * 
     * @param value T
     * @return abstract
     * @ignored execute
     */
    protected abstract void execute( T value );
}
