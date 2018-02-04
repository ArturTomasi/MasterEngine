package com.me.eng.ui.panes;

import com.me.eng.ui.apps.ApplicationUI;
import com.me.eng.ui.parts.ApplicationViewButton;
import com.me.eng.ui.views.ApplicationViewUI;
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
