package com.mn.engcivil.interfaces.apps;

import com.mn.engcivil.annotations.ApplicationDescriptor;
import com.mn.engcivil.application.ApplicationContext;
import com.mn.engcivil.application.ResourceLocator;
import com.mn.engcivil.interfaces.panes.ApplicationCaption;
import com.mn.engcivil.interfaces.panes.StatusBar;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Matheus
 */
public class LauncherApplication
    extends 
        StandaloneApplication
{
    public LauncherApplication()
    {
        initComponents();
        
        addApplication( AdministratorApplicationUI.class );
        addApplication( SetupApplicationUI.class );
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
    
    public void refreshContent()
    {
        statusBar.setUser( ApplicationContext.getInstance()
                              .getActiveUser() );
    }
    
    private void initComponents()
    {
        setSclass( "launcher-app" );
        
        inner.setWidgetAttribute( "align", "center" );
        inner.setSclass( "launcher-app-container" );
        inner.setStyle( "display: table-cell; vertical-align: middle" );
        
        content.appendChild( inner );
        
        content.setStyle( "display: table" );
        content.setHflex( "true" );
        content.setVflex( "true" );
        
        borderlayout.appendChild( new North() );
        borderlayout.appendChild( new Center() );
        borderlayout.appendChild( new South() );
        
        borderlayout.getNorth().setHeight( "70px" );
        borderlayout.getNorth().setBorder( "none" );
        borderlayout.getNorth().setStyle( "background: transparent" );
        borderlayout.getNorth().appendChild( caption );
        
        borderlayout.getSouth().setHeight( "20px" );
        borderlayout.getSouth().setBorder( "none" );
        borderlayout.getSouth().setStyle( "background-color: rgb(48, 67, 105);" );
        borderlayout.getSouth().appendChild( statusBar );
        
        borderlayout.getCenter().setBorder( "none" );
        borderlayout.getCenter().setStyle( "background: transparent" );
        borderlayout.getCenter().appendChild( content );
        
        borderlayout.setStyle( "background: transparent" );
        
        appendChild( borderlayout );
        
        addEventListener( org.zkoss.zk.ui.event.Events.ON_CLIENT_INFO, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                ClientInfoEvent e = (ClientInfoEvent) t;
                
                setWidth( e.getDesktopWidth() + "px" );
                setHeight( e.getDesktopHeight() + "px" );
            }
        } );
    }
    
    private Div content = new Div();
    private Div inner = new Div();
    
    private Borderlayout borderlayout = new Borderlayout();
    
    private ApplicationCaption caption = new ApplicationCaption();
    
    private StatusBar statusBar = new StatusBar();
}
