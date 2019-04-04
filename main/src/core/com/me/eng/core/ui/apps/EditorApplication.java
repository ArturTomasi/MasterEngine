/*
 * Filename:    EditorApplication 
 *
 * Author:      Artur Tomasi
 * EMail:       tomasi.artur@gmail.com
 * Internet:    https://www.masterengine.com.br
 *
 * Copyright © 2019 by Over Line Ltda.
 * 95900-038, LAJEADO, RS
 * BRAZIL
 *
 * The copyright to the computer program(s) herein
 * is the property of Over Line Ltda., Brazil.
 * The program(s) may be used and/or copied only with
 * the written permission of Over Line Ltda.
 * or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which
 * the program(s) have been supplied.
 */
package com.me.eng.core.ui.apps;

import com.me.eng.core.annotations.ApplicationDescriptor;
import com.me.eng.core.ui.events.EventFactory;
import com.me.eng.core.ui.events.EventLookup;
import com.me.eng.core.ui.util.GenericObserver;
import com.me.eng.core.ui.views.ApplicationViewUI;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Artur Tomasi
 */
@ApplicationDescriptor( url = "/admin/request.jsp",
                        icon = "requests/ai_request.png",
                        module= "eng_request",
                        label = "Adminstração de Requisções" )
public class EditorApplication 
    extends 
        ApplicationUI
{

    /**
     * RequestApplicationUI
     * 
     */
    public EditorApplication() 
    {
        ApplicationViewUI view = new ApplicationViewUI() 
        {
            @Override
            protected void initComponents() 
            {
                final Textbox txt = new Textbox();
                txt.setMultiline( true );
                txt.setWidth( "100%" );
                txt.setHeight( "100%" );
                
                appendChild( txt );
                
                EventFactory.subscribe( EventLookup.UPDATE_TEXT, new GenericObserver<String>() 
                {
                    @Override
                    public void notify( String source )
                    {
                        txt.setText( source );
                    }
                } );
                
                txt.addEventListener( Events.ON_CHANGING, new EventListener<Event>() {
                    @Override
                    public void onEvent(Event t) throws Exception 
                    {
                        EventFactory.publish(EventLookup.UPDATE_TEXT, ((InputEvent) t).getValue() );
                    }
                } );
                        
            }
        };
        
        addView( view );
    }
    
}
