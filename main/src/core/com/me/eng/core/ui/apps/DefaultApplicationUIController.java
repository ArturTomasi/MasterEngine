/* 
 *  Filename:    DefaultApplicationUIController 
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

import com.me.eng.core.application.ApplicationInject;
import com.me.eng.core.license.controller.LicenseManager;
import com.me.eng.core.license.exceptions.LicenseException;
import com.me.eng.core.services.ApplicationServices;
import com.me.eng.core.ui.views.ApplicationViewUI;
import java.util.Observable;
import java.util.Observer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
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
    
    /**
     * doAfterCompose
     * 
     * @param comp Component
     * @throws Exception
     */
    @Override
    public void doAfterCompose( final Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        
        applicationInject.init();

        LicenseManager.getInstance().consumeLicense( Executions.getCurrent().getParameter( "module" )  );
        
        ApplicationUI ui = (ApplicationUI) Class.forName( Executions.getCurrent().getParameter( "ui" ) ).newInstance();

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
