/* 
 *  Filename:    DefaultApplicationUIPane 
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
package com.me.eng.core.ui.apps;

import com.google.common.base.Objects;
import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.ui.panes.ApplicationCaption;
import com.me.eng.core.ui.panes.ApplicationViewMenuBar;
import com.me.eng.core.ui.views.ApplicationViewUI;
import java.util.LinkedList;
import java.util.List;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkmax.zul.Nav;
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zkmax.zul.Navitem;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.West;

/**
 *
 * @author Matheus
 */
public class DefaultApplicationUIPane
    extends 
        Div
{
    private ApplicationViewUI viewUI;
    
    /**
     * DefaultApplicationUIPane
     * 
     */
    public DefaultApplicationUIPane()
    {
        initComponents();
    }
    
    /**
     * resize
     * 
     * @param e ClientInfoEvent
     */
    public void resize( ClientInfoEvent e )
    {
        borderlayout.setWidth( e.getDesktopWidth() + "px" );
        borderlayout.setHeight( e.getDesktopHeight() + "px" );
        borderlayout.resize();
    }
    
    /**
     * setApplicationUI
     * 
     * @param ui ApplicationUI
     */
    public void setApplicationUI( ApplicationUI ui )
    {
        applicationViewMenuBar.setApplicationUI( ui );
        
        activeView( ui.getFirstView() );
        
        applicationViewMenuBar.setUser( ApplicationContext.getInstance().getActiveUser() );
    }
    
    /**
     * setVisibleMenu
     * 
     * @param visible boolean
     */
    public void setVisibleMenu( boolean visible )
    {
        borderlayout.getWest().setVisible( visible );
    }
    
    /**
     * activeView
     * 
     * @param view ApplicationViewUI
     */
    public void activeView( ApplicationViewUI view )
    {
        if ( ! Objects.equal( viewUI, view ) )
        {
            this.viewUI = view;

            setVisibleMenu( ! view.getActions().isEmpty() );
            
            List<ActionCategory> actions = new LinkedList();
            actions.addAll( view.getActions() );
            actions.add( systemActions );

            Navbar navbar = new Navbar( "vertical" );
            
            actions.forEach( category ->
            {
                Nav nav = new Nav( category.getLabel() );

                category.getActions().forEach( a -> 
                {
                    Navitem item = new Navitem();
                    item.setLabel( a.getLabel() );
                    item.setImage( a.getIcon() );
                    item.setTooltiptext( a.getTooltipText() );
                    item.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, a );

                    nav.appendChild( item );
                } );

                navbar.appendChild( nav );
            } );

            borderlayout.getWest().getChildren().clear();
            borderlayout.getWest().appendChild( navbar  );

            borderlayout.getCenter().getChildren().clear();
            borderlayout.getCenter().appendChild( view  );

            view.active();
        }
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        borderlayout.appendChild( new North() );
        borderlayout.appendChild( new Center() );
        borderlayout.appendChild( new West() );
        borderlayout.appendChild( new South() );

        borderlayout.getWest().setTitle( "Menu" );
        
        borderlayout.getNorth().setHeight( "70px" );
        borderlayout.getWest().setWidth( "150px" );
        borderlayout.getSouth().setHeight( "70px" );
        
        borderlayout.getCenter().setVflex( "true" );
        borderlayout.getCenter().setHflex( "true" );
        
        borderlayout.getWest().setSclass( "default-app-pane-west" );
        borderlayout.getSouth().setSclass( "default-app-pane-south" );
        borderlayout.getNorth().setSclass( "default-app-pane-north" );
        
        borderlayout.getNorth().setBorder( "none" );
        borderlayout.getCenter().setBorder( "none" );
        borderlayout.getWest().setBorder( "none" );
        
        borderlayout.getNorth().appendChild( caption ); 

        borderlayout.getSouth().appendChild( applicationViewMenuBar );
        
        appendChild( borderlayout );
    }

    private Borderlayout borderlayout = new Borderlayout();
    
    private ApplicationCaption caption = new ApplicationCaption();
    private ApplicationViewMenuBar applicationViewMenuBar = new ApplicationViewMenuBar();
    
    private Action logoffAction = new Action( "core/tb_logout.png", "Sair", "Sair do Sistema")
    {
        
        @Override
        public void onEvent( Event t ) throws Exception
        {
            Sessions.getCurrent().invalidate();
            
            Executions.getCurrent().sendRedirect( "/" );
        }
    };
    
    private ActionCategory systemActions = new ActionCategory( "Sistema", logoffAction );
}
