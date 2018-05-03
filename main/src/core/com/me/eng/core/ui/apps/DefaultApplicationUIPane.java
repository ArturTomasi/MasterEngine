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
import com.me.eng.core.ui.panes.ApplicationViewMenu;
import com.me.eng.core.ui.panes.StatusBar;
import com.me.eng.core.ui.views.ApplicationViewUI;
import java.util.LinkedList;
import java.util.List;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Vlayout;
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
        menu.setApplicationUI( ui );
        
        activeView( ui.getFirstView() );
        
        statusBar.setUser( ApplicationContext.getInstance().getActiveUser() );
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
            
            borderlayout.getCenter().getChildren().clear();

            Menubar menubar = new Menubar();

            List<ActionCategory> actions = new LinkedList();
            actions.addAll( view.getActions() );
            actions.add( systemActions );
            
            for ( ActionCategory category : actions )
            {
                Menu menu = new Menu( category.getLabel() );

                Menupopup menupopup = new Menupopup();

                for ( Action a : category.getActions() )
                {
                    Menuitem item = new Menuitem( a.getLabel() );
                    item.setImage( a.getIcon() );
                    item.setTooltiptext( a.getTooltipText() );
                    item.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, a );

                    menupopup.appendChild( item );
                }

                menu.appendChild( menupopup );

                menubar.appendChild( menu );
            }

            Vlayout div = new Vlayout();
            div.setVflex( "true" );
            div.setHflex( "true" );
            div.appendChild( menubar );
            div.appendChild( view );

            view.setStyle( "padding: 5px" );

            borderlayout.getCenter().appendChild( div );

            view.active();
            
            borderlayout.getWest().setVisible( view.getApplicationUI().getViews().size() > 1 );
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

        borderlayout.getWest().setCollapsible( true );
        borderlayout.getWest().setTitle( "Funções" );
        
        borderlayout.getNorth().setHeight( "70px" );
        borderlayout.getWest().setWidth( "100px" );
        borderlayout.getSouth().setHeight( "20px" );
        borderlayout.getCenter().setVflex( "true" );
        borderlayout.getCenter().setHflex( "true" );
        
        borderlayout.getWest().setSclass( "default-app-pane-west" );
        borderlayout.getSouth().setSclass( "default-app-pane-south" );
        borderlayout.getNorth().setSclass( "default-app-pane-north" );
        
        borderlayout.getNorth().setBorder( "none" );
        borderlayout.getCenter().setBorder( "none" );
        borderlayout.getWest().setBorder( "none" );
        
        borderlayout.getWest().appendChild( menu );
        borderlayout.getNorth().appendChild( caption ); 

        borderlayout.getSouth().appendChild( statusBar );
        
        appendChild( borderlayout );
    }
    
    private ApplicationCaption caption = new ApplicationCaption();
    private ApplicationViewMenu menu = new ApplicationViewMenu();
    private Borderlayout borderlayout = new Borderlayout();
    
    private StatusBar statusBar = new StatusBar();
    
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
