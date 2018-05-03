/* 
 *  Filename:    ApplicationViewMenu 
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
package com.me.eng.core.ui.panes;

import com.me.eng.core.ui.apps.ApplicationUI;
import com.me.eng.core.ui.parts.ApplicationViewButton;
import com.me.eng.core.ui.views.ApplicationViewUI;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Matheus
 */
public class ApplicationViewMenu
    extends 
        Vlayout
{
    private ApplicationViewButton selectedButton;
    
    public ApplicationViewMenu()
    {
        setStyle( "margin-top: 10px" );
        setVflex( "true" );
    }
    
    public void setApplicationUI( final ApplicationUI ui )
    {
        for ( final ApplicationViewUI viewUI : ui.getViews() )
        {
            final ApplicationViewButton bt = new ApplicationViewButton( viewUI );
         
            bt.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
            {
                @Override
                public void onEvent( Event t ) throws Exception
                {
                    selectedButton.setSelected( false );
                    selectedButton = bt;
                    selectedButton.setSelected( true );
                    
                    ui.setSelectedView( viewUI );
                }
            } );
            
            appendChild( bt );
            
            if ( selectedButton == null )
            {
                selectedButton = bt;
                selectedButton.setSelected( true );
            }
        }
    }
}
