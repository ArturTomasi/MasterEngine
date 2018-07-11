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
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Fisheye;
import org.zkoss.zkex.zul.Fisheyebar;

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
        
        Fisheye fisheye = new Fisheye( app.label(), ResourceLocator.getImageResource( app.icon() ) );
        fisheye.addEventListener( Events.ON_CLICK, e->  Executions.getCurrent().sendRedirect( app.url(), "_blank" ) );
        
        bar.appendChild( fisheye );
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
        
        setVflex( "true" );
        
        bar.setItemHeight( 150 );
        bar.setItemWidth( 150 );
        bar.setItemMaxHeight( 350 );
        bar.setItemMaxWidth( 350 );
        
        appendChild( bar );
    }
    
    private Fisheyebar bar = new Fisheyebar();
}
