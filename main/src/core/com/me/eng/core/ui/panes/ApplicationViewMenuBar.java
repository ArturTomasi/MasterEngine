/* 
 *  Filename:    StatusBar 
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

import com.me.eng.BuildInfo;
import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.application.ResourceLocator;
import com.me.eng.core.domain.User;
import com.me.eng.core.ui.apps.ApplicationUI;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;

/**
 *
 * @author Artur
 */
public class ApplicationViewMenuBar
    extends 
        Div
{
    /**
     * StatusBar
     * 
     */
    public ApplicationViewMenuBar()
    {
        initComponents();
    }
    
    /**
     * setApplicationUI
     * 
     * @param ui ApplicationUI
     */
    public void setApplicationUI( ApplicationUI ui )
    {
        menu.setApplicationUI( ui );
    }
    
    /**
     * setUser
     * 
     * @param user User
     */
    public void setUser( User user )
    {
        if ( user != null )
        {
            lbUser.setValue( user.getName() );
        }
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        lbUser.setValue( "(Sem usuário autenticado)" );
        lbVersion.setValue( BuildInfo.getVersion() );
        
        setHflex( "true" );
        setVflex( "true" );
        setStyle( "color: white; font-size: 10px; position: relative;" );

        imgUser.setSrc( ResourceLocator.getImageResource( "core/sb_user.png" ) );
        imgUser.setHeight( "18px" );
        imgUser.setWidth( "auto" );
        
        lbVersion.setStyle( "position: absolute; right: 10px; bottom: 22px" );
        lbUser.setStyle( "color: #fff;" );
        
        Hbox hbox = new Hbox();
        hbox.setAlign( "middle" );
        hbox.appendChild( lbUser );
        hbox.appendChild( imgUser );
        hbox.setTooltiptext( "Clique para fazer logout!" );
        hbox.setStyle( "position: absolute; left: 10px; bottom: 22px; cursor: pointer;" );
        
        appendChild( hbox );
        
        ApplicationContext.getInstance().getRoot().appendChild( menu );
        appendChild( lbVersion );
        
        hbox.addEventListener( Events.ON_CLICK, e->  
        {
            Sessions.getCurrent().invalidate();
            
            Executions.getCurrent().sendRedirect( "/" );
        } );
    }
    
    private Image imgUser = new Image();
    private Label lbUser = new Label();
    private Label lbVersion = new Label();
    
    private ApplicationViewMenu menu = new ApplicationViewMenu();
}
