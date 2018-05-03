/* 
 *  Filename:    LauncherApplicationViewUI 
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
package com.me.eng.core.ui.views;

import com.me.eng.core.annotations.ApplicationDescriptor;
import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.application.ResourceLocator;
import com.me.eng.samples.ui.apps.AdministratorApplicationUI;
import com.me.eng.core.ui.apps.ApplicationUI;
import com.me.eng.finances.ui.apps.FinanceApplicationUI;
import com.me.eng.samples.ui.apps.PendenciesApplicationUI;
import com.me.eng.core.ui.apps.SetupApplicationUI;
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

    /**
     * LauncherApplicationViewUI
     * 
     */
    public LauncherApplicationViewUI()
    {
        setIcon( "" );
        setLabel( "Aplicações" );
    }
    
    /**
     * addApplication
     * 
     * @param extends Class&lt;?
     * @param ui ApplicationUI&gt;
     */
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

    /**
     * initComponents
     * 
     */
    @Override
    protected void initComponents()
    {
        addApplication( PendenciesApplicationUI.class );
        addApplication( AdministratorApplicationUI.class );
        addApplication( FinanceApplicationUI.class );
        
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
