/* 
 *  Filename:    Bootstrap 
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
package com.me.eng.core.application;

import com.me.eng.core.infrastructure.NotificationManager;
import com.me.eng.core.license.controller.LicenseManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Matheus
 */
@WebListener
public class Bootstrap
    implements 
        ServletContextListener
{
    /**
     * contextInitialized
     * 
     * @param sce ServletContextEvent
     */
    @Override
    public void contextInitialized( ServletContextEvent sce )
    {
        ApplicationContext.setContextPath( sce.getServletContext().getContextPath() );
        
        NotificationManager.getInstance().start();
        
        LicenseManager.getInstance().load();
        
        LicenseManager.getInstance().cleanup();
    }

    /**
     * contextDestroyed
     * 
     * @param sce ServletContextEvent
     */
    @Override
    public void contextDestroyed( ServletContextEvent sce )
    {
        NotificationManager.getInstance().stop();
    }
}
