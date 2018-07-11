/* 
 *  Filename:    ApplicationContext 
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

import com.me.eng.core.services.ApplicationServices;
import com.google.common.base.Strings;
import com.me.eng.samples.domain.SampleFilter;
import com.me.eng.core.domain.User;
import com.me.eng.core.license.controller.LicenseManager;
import com.me.eng.core.ui.util.Prompts;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

/**
 *
 * @author Matheus
 */
public class ApplicationContext
{
    private static ApplicationContext instance;
    
    private static String contextPath = "/eng";
    
    /**
     * getInstance
     * 
     * @return ApplicationContext
     */
    public static ApplicationContext getInstance()
    {
        if ( instance == null )
        {
            instance = new ApplicationContext();
        }
        
        return instance;
    }

    /**
     * getContextPath
     * 
     * @return String
     */
    public static String getContextPath()
    {
        return contextPath;
    }

    /**
     * setContextPath
     * 
     * @param contextPath String
     */
    public static void setContextPath( String contextPath )
    {
        ApplicationContext.contextPath = contextPath;
    }
    
    /**
     * getSharedSampleFilter
     * 
     * @return SampleFilter
     */
    public SampleFilter getSharedSampleFilter()
    {
        SampleFilter filter = (SampleFilter) Sessions.getCurrent().getAttribute( SessionVariables.ACTIVE_SAMPLE_FILTER );
        
        if ( filter == null )
        {
            filter = new SampleFilter();
            
            Sessions.getCurrent().setAttribute( SessionVariables.ACTIVE_SAMPLE_FILTER, filter );
        }
        
        return filter;
    }

    /**
     * ApplicationContext
     * 
     */
    private ApplicationContext() {}

    
    /**
     * getActiveUser
     * 
     * @return User
     */
    public User getActiveUser()
    {
        User user = (User) Sessions.getCurrent().getAttribute( SessionVariables.ACTIVE_USER );
        
        if ( user == null )
        {
            if ( Executions.getCurrent() != null )
            {
                Principal principal = Executions.getCurrent().getUserPrincipal();

                try
                {
                    user = ApplicationServices.getCurrent()
                                .getUserRepository()
                                .findByLogin( principal.getName() );

                    LicenseManager.getInstance().cleanup( user );
                    
                    setAttribute( SessionVariables.ACTIVE_USER, user );
                }

                catch ( Exception e )
                {
                    logException( e );
                }
            }
        }
        
        return user;
    }
    
    /**
     * setAttribute
     * 
     * @param key String
     * @param value Object
     */
    public void setAttribute( String key, Object value )
    {
        Sessions.getCurrent().setAttribute( key, value );
    }
    
    /**
     * T
     * 
     * @param key String
     * @return &lt;T&gt;
     * @ignored getAttribute
     */
    public <T> T getAttribute( String key )
    {
        return (T) Sessions.getCurrent().getAttribute( key );
    }
    
    /**
     * handleException
     * 
     * @param e Throwable
     */
    public void handleException( Throwable e )
    {
        logException( e );
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter( sw );
        
        e.printStackTrace( pw );
        
        Prompts.error( sw.toString() );
    }
    
    /**
     * getRoot
     * 
     * @return Component
     */
    public Component getRoot()
    {
        Execution execution = Executions.getCurrent();
        
        if ( execution != null && execution.getDesktop() != null )
        {
            return execution.getDesktop().getFirstPage().getFirstRoot();
            
        }
        
        return null;
    }
    
    /**
     * logInfo
     * 
     * @param info String
     */
    public void logInfo( String info )
    {
        Logger.getGlobal().log( Level.INFO, info );
    }
    
    /**
     * logException
     * 
     * @param e Throwable
     */
    public void logException( Throwable e )
    {
        Logger.getGlobal().log( Level.SEVERE, null, e );
    }
}
