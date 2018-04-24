/*
 *  Filename:    EventLookup
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
package com.me.eng.ui.events;

import org.zkoss.zk.ui.event.EventQueues;

/**
 *
 * @author Artur Tomasi
 */
public enum EventLookup
{
    UPDATE_SAMPLE       ( "onUpdateSample", EventQueues.APPLICATION ),
    DELETE_SAMPLE       ( "onDeleteSample", EventQueues.APPLICATION );

    private String name, type;

    /**
     * EventLookup
     * 
     * @param name String
     * @param type String
     */
    private EventLookup( String name, String type ) 
    {
        this.name = name;
        this.type = type;
    }

    /**
     * getName
     * 
     * @return String
     */
    public String getName() 
    {
        return name;
    }

    /**
     * getType
     * 
     * @return String
     */
    public String getType() 
    {
        return type;
    }
}