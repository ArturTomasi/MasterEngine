/*
 *  Filename:    WebAppInit
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
package com.me.eng.core.ui.apps.listeners;

import com.me.eng.core.license.controller.LicensePurger;
import org.zkoss.zk.au.http.DHtmlUpdateServlet;
import org.zkoss.zk.ui.WebApp;

/**
 *
 * @author Artur Tomasi
 */
public class WebAppInit 
    implements 
        org.zkoss.zk.ui.util.WebAppInit
{

    /**
     * init
     * 
     * @param webapp WebApp
     * @throws Exception
     */
    @Override
    public void init( WebApp webapp ) throws Exception 
    {
        DHtmlUpdateServlet.addAuExtension( webapp, "/purgeLicense",   new LicensePurger() );
    }
    
}
