package com.mn.engcivil.interfaces.apps;

import com.mn.engcivil.application.ApplicationInject;
import com.mn.engcivil.interfaces.views.ApplicationViewUI;
import java.util.Observable;
import java.util.Observer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Window;

/**
 *
 * @author Matheus
 */
@VariableResolver(org.zkoss.zkplus.cdi.DelegatingVariableResolver.class)
public class DefaultApplicationUIController
    extends 
        SelectorComposer
{
    @WireVariable 
    ApplicationInject applicationInject;
    
    @Wire
    DefaultApplicationUIPane applicationPane;
    
    @Override
    public void doAfterCompose( final Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
     
        applicationInject.init();
        
        ApplicationUI ui = (ApplicationUI) Class.forName( Executions.getCurrent().getParameter( "ui" ) )
                                    .newInstance();
        
        applicationPane.setApplicationUI( ui );
        
        ui.addObserver( new Observer()
        {
            @Override
            public void update( Observable o, Object arg )
            {
                applicationPane.activeView( (ApplicationViewUI) arg );
            }
        } );
        
        comp.addEventListener( "onClientInfo", new EventListener<ClientInfoEvent>()
        {
            @Override
            public void onEvent( ClientInfoEvent t ) throws Exception
            {
                ( (Window) comp ).setWidth( t.getDesktopWidth() + "px" );
                ( (Window) comp ).setHeight( t.getDesktopHeight() + "px" );
                
                applicationPane.resize( t );
            }
        } ); 
    }
}
