package com.mn.engcivil.interfaces.views;

import com.mn.engcivil.annotations.ApplicationDescriptor;
import com.mn.engcivil.application.ApplicationContext;
import com.mn.engcivil.application.ResourceLocator;
import com.mn.engcivil.interfaces.apps.AdministratorApplicationUI;
import com.mn.engcivil.interfaces.apps.ApplicationUI;
import com.mn.engcivil.interfaces.apps.PendenciesApplicationUI;
import com.mn.engcivil.interfaces.apps.SetupApplicationUI;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Vbox;

/**
 *
 * @author Matheus
 */
public class LauncherApplicationViewUI
    extends 
        ApplicationViewUI
{

    public LauncherApplicationViewUI()
    {
        setIcon( "" );
        setLabel( "Aplicações" );
    }
    
    private void addApplication( Class<? extends ApplicationUI> ui )
    {
        final ApplicationDescriptor app = ui.getAnnotation( ApplicationDescriptor.class );
        
        Vbox div = new Vbox();
        div.setSclass( "launcher-app-button" );
        div.setAlign( "middle" );
        div.appendChild( new Image( ResourceLocator.getImageResource( app.icon() ) ) );
        div.appendChild( new Label( app.label() ) );
        
        div.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                Executions.getCurrent().sendRedirect( app.url(), "_blank" );
            }
        } );
        
        inner.appendChild( div );
    }

    @Override
    protected void initComponents()
    {
        addApplication( PendenciesApplicationUI.class );
        addApplication( AdministratorApplicationUI.class );
        
        if ( ApplicationContext.getInstance().getActiveUser().isAdministrator() )
        {
            addApplication( SetupApplicationUI.class );
        }
        
        setSclass( "launcher-app" );
        setVflex( "true" );
        
        inner.setWidgetAttribute( "align", "center" );
        inner.setSclass( "launcher-app-container" );
        inner.setStyle( "display: table-cell; vertical-align: middle" );
        
        content.appendChild( inner );
        
        content.setStyle( "display: table" );
        content.setHflex( "true" );
        content.setVflex( "true" );
        
        appendChild( content );
    }
    
    private Div content = new Div();
    private Div inner = new Div();
}
