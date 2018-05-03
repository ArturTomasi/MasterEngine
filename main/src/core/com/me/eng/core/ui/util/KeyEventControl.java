/*
 *  Filename:    KeyEventControl
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
package com.me.eng.core.ui.util;

import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author Artur Tomasi
 */
public class KeyEventControl 
{
    /**
     * Key
     */
    public static enum Key
    {
        CTRL_ALT_N  ( 78, true, true, false );
        
        public int code;
        public boolean ctrl, alt, shift;

        /**
         * Key
         * 
         * @param code int
         * @param ctrl boolean
         * @param alt boolean
         * @param shift boolean
         */
        Key( int code, boolean ctrl, boolean alt, boolean shift ) 
        {
            this.code   = code;
            this.ctrl   = ctrl;
            this.alt    = alt;
            this.shift  = shift;
        }
        
        /**
         * toString
         * 
         * @return String
         */
        @Override
        public String toString()
        {
            return "{" +
                        "ordinal: " + ordinal() + ", " +
                        "code: "    + code      + "," +
                        "ctrl: "    + ctrl      + "," +
                        "alt:  "    + alt       + "," +
                        "shift: "   + shift     +
                   "}";
        }
    }
    
    public static final class Events
    {
        public static final String ON_KEY_PRESSED = "onKeyPressed"; 
    }
    
    private String _variable;
    private Component _parent;
    
    /**
     * KeyEventControl
     * 
     * @param parent Component
     */
    public KeyEventControl( Component parent ) 
    {
        this._parent  = parent;
        
        this._variable = "key_" + _parent.getUuid();
        
        init();
    }
    
    /**
     * addKey
     * 
     * @param key Key
     */
    public void addKey( Key key )
    {
        Clients.evalJavaScript( _variable + ".key( " + key + " )" );
    }
    
    /**
     * post
     * 
     * @param t Event
     */
    private void post( Event t )
    {
        if ( t.getData() instanceof Map )
        {
            Map attr = Map.class.cast( t.getData() );
            
            if ( attr.containsKey( "ordinal" ) )
            {
                Key key = Key.values()[ (Integer)attr.get( "ordinal" ) ];
                
                Executions.getCurrent().postEvent( new Event( Events.ON_KEY_PRESSED, _parent, key ) );
            }
        }
    }
    
    /**
     * init
     * 
     */
    private void init()
    {
        _parent.setWidgetAttribute( "key-event-control",  _parent.getUuid() );
        
        Clients.evalJavaScript( " var " + _variable + " = new KeyEventControl(); " +
                
                                _variable + ".target( '" + _parent.getUuid() + "' ); " +
                
                                _variable + ".enable(); " );
        
        _parent.addEventListener( "onCustomKeyDown", new EventListener<Event>() 
        {
            @Override
            public void onEvent( Event t ) throws Exception 
            {
                post( t );
            }
        } );
    }
}   
