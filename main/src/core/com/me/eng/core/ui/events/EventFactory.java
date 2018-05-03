/*
 *  Filename:    EventFactory
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
 *  is the property of Over Line Ltda.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda,
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.core.ui.events;

import com.me.eng.core.ui.util.GenericObserver;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;

/**
 *
 * @author Artur Tomasi
 */
public class EventFactory 
{
    /**
     * publish
     * 
     * @param lookup EventLookup
     * @param data Object
     */
    public static void publish( EventLookup lookup, Object data )
    {
        EventQueues.lookup( lookup.getName(), lookup.getType(), true ).publish( new Event( lookup.getName(), null, data ) );
    }
    
    /**
     * subscribe
     * 
     * @param lookup EventLookup
     * @param observer GenericObserver
     */
    public static void subscribe( EventLookup lookup, GenericObserver observer )
    {
        EventQueues.lookup( lookup.getName(), lookup.getType(), true ).subscribe( (Event evt) -> 
        {
            observer.notify( evt.getData() );
        } );
    }
}
