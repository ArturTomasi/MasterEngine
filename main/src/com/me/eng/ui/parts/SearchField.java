/* 
 *  Filename:    SearchField 
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
package com.me.eng.ui.parts;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Matheus
 */
public class SearchField
    extends 
        Div
{
    public static final class Events
    {
        public static final String ON_SEARCH = "onSearch";
    }
    
    public SearchField()
    {
        initComponents();
    }
    
    public String getText()
    {
        return tfSearch.getValue();
    }
    
    private void initComponents()
    {
        tfSearch.setHflex( "true" );
        
        Label lbSearch = new Label( "Buscar:" );
        
        Hbox hbox = new Hbox();
        hbox.setHflex( "true" );
        hbox.setAlign( "center" );
        hbox.setSpacing( "5px" );
        hbox.appendChild( lbSearch );
        hbox.appendChild( tfSearch );
        
        appendChild( hbox );
        
        tfSearch.addEventListener( org.zkoss.zk.ui.event.Events.ON_OK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                Executions.getCurrent().postEvent( new Event( Events.ON_SEARCH, SearchField.this ) );
            }
        } );
    }
    
    private Textbox tfSearch = new Textbox();
}
