/*
 *  Filename:    LicenseManager
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
package com.me.eng.license.controller;

import com.me.eng.application.ApplicationContext;
import com.me.eng.application.ConfigurationManager;
import com.me.eng.domain.License;
import com.me.eng.domain.User;
import com.me.eng.domain.repositories.LicenseRepository;
import com.me.eng.domain.repositories.SampleRepository;
import com.me.eng.license.exceptions.LicenseException;
import com.me.eng.license.signature.LicenseConstants;
import com.me.eng.license.signature.LicenseValidator;
import com.me.eng.services.ApplicationServices;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.inject.spi.CDI;
import javax.servlet.http.HttpSession;
import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.UnboundLiteral;
import org.jboss.weld.environment.se.WeldContainer;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

/**
 *
 * @author Artur Tomasi
 */
public class LicenseManager 
{
    private static LicenseManager instance;
    
    /**
     * getInstance
     * 
     * @return LicenseManager
     */
    public static LicenseManager getInstance()
    {
        if ( instance == null )
        {
            instance = new LicenseManager();
        }
        
        return instance;
    }
    
    private final Map<String, Integer> licensedModules       = new HashMap();
    private final Map<String, Map<String, License>> sessions = new HashMap();
    
    private boolean isValidPeriod = true;
    
    /**
     * LicenseController
     * 
     */
    public LicenseManager(){}
    
    /**
     * load
     */
    public void load()
    {
        Thread listener = new Thread( new Runnable() 
        {
            @Override
            public void run() 
            {
                do 
                {         
                    try 
                    {
                        ConfigurationManager.clearSystemProperty( "license_exception" );

                        LicenseValidator validator = new LicenseValidator();

                        if ( validator.verifyLicense( LicenseConstants.PUBLIC_KEY_PATH.stringValue(), LicenseConstants.LICENSE_PATH.stringValue() ) )
                        {
                            Map<String, String> values = validator.getLicenseOptions();

                            Date dateStart   = new Date( Long.valueOf( values.get( "date_start" ) ) );
                            Date dateEnd     = new Date( Long.valueOf( values.get( "date_end" ) ) );
                            Date today       = new Date( System.currentTimeMillis() );

                            if ( today.after( dateEnd ) )
                            {
                                isValidPeriod = false;
                            }

                            if ( today.before( dateStart ) )
                            {
                               isValidPeriod = false;
                            }
                            
                            if ( isValidPeriod )
                            {
                                values.forEach( (key, value) ->
                                {
                                    if ( key.startsWith( "max_eng" ) )
                                    {
                                        licensedModules.put( key.substring( 4 ), Integer.valueOf( value ) );
                                    }
                                } );
                            }
                        }
                        else

                        {
                            ConfigurationManager.setSystemProperty( "license_exception", "true" );
                        }
                    
                        Thread.sleep( ( 12 * 60 * 60 * 1000 ) );
                    } 
                    
                    catch ( Exception e )
                    {
                         ConfigurationManager.setSystemProperty( "license_exception", "true" );
                    }
                    
                }
                
                while ( true );
            }
        } );
        
        listener.setDaemon( true );
        listener.start();
    }
    
    /**
     * clear
     */
    public void cleanup()
    {
        try 
        {
            CDI cdi = WeldContainer.current();

            LicenseRepository sampleRepository = (LicenseRepository) cdi.select( LicenseRepository.class ).get();

            RequestContext context = (RequestContext) cdi.select( RequestContext.class, UnboundLiteral.INSTANCE ).get();
            context.activate();

            try
            {
                sessions.clear();

                sampleRepository.cleanup();
            }

            finally
            {
                context.deactivate();
            }
        
        }
        
        catch ( Exception e )
        {
            ApplicationContext.getInstance().logException( e );
        }
    }
    
    
    /**
     * cleanup
     * 
     * @param user User
     * @throws Exception
     */
    public void cleanup( User user ) throws Exception
    {
        sessions.forEach( ( module, licenses ) ->
        {
            licenses.values().removeIf( license -> license.getUser().equals( user ) );
        } );
        
        ApplicationServices.getCurrent().getLicenseRepository().cleanup( session(), user );
    }
    
    /**
     * consumeLicense
     * 
     * @param module String
     */
    public void consumeLicense( String module )
    {
        if ( module == null )
        {
            throw new IllegalArgumentException( "Module cannot be null!" );
        }
        
        try
        {
            if ( ! isValidPeriod )
            {
                throw new LicenseException( LicenseException.Type.INVALID_DATE );
            }
        
            Integer max = licensedModules.getOrDefault( module, -1 );

            if ( max == -1 )
            {
                throw new LicenseException( LicenseException.Type.MODULE_NOT_LICENSED );
            }

            License license = new License();
            license.setModule( module );
            license.setSession( session() );
            license.setUser( user() );
            license.setDate( new Date( System.currentTimeMillis() ) );

            synchronized ( sessions )
            {
                Map<String, License> sessionByModule = sessions.getOrDefault( module, new HashMap() );
                
                License sessionLicense = sessionByModule.get( license.key() );

                if ( sessionLicense != null )
                {
                    sessionLicense.lease();

                    if ( max < sessionByModule.values().size() )
                    {
                        throw new LicenseException( LicenseException.Type.MAX_SEAT );
                    }
                    
                    sessionByModule.put( license.key(), sessionLicense );

                    sessions.put( module, sessionByModule );

                }        

                else 
                {
                    license.lease();

                    sessionByModule.put( license.key(), license );
                    
                    sessions.put( module, sessionByModule );

                    /**
                     * consume the license
                     */
                     ApplicationServices.getCurrent().getLicenseRepository().add( license );
                }
            }
        }
        
        catch ( LicenseException e )
        {
            foward( e );
        }
        
        catch ( Exception e )
        {
            ApplicationContext.getInstance().logException( e );
        }  
    }
    
    /**
     * purgeLicense
     * 
     * @param sn
     * @throws Exception
     */
    public void purgeLicense( Session sn ) throws Exception
    {
        synchronized ( sessions )
        {
            sessions.forEach( (module, map ) ->
            {
                map.values().removeIf( license ->
                {
                    boolean remove = license.getSession().equals( session( sn ) );
                    
                    try
                    {
                        if ( remove )
                        {
                            ApplicationServices.getCurrent().getLicenseRepository().purge( license );
                        }
                    }
                    
                    catch ( Exception e )
                    {
                        ApplicationContext.getInstance().logException( e );
                    }
                    
                    return remove;
                } );
            } );
        }
    }
    
    /**
     * purgeLicense
     * 
     * @param module String
     * @throws Exception
     */
    public void purgeLicense( String module ) throws Exception
    {
        if ( module == null )
        {
            throw new IllegalArgumentException( "Module cannot be null!" );
        }
        
        License license = new License();
        license.setModule( module );
        license.setSession( session() );
        license.setUser( user() );
        license.setDate( new Date( System.currentTimeMillis() ) );
        
        synchronized ( sessions )
        {
            Map<String, License> sessionByModule = sessions.getOrDefault( module, new HashMap() );
                
            License sessionLicense = sessionByModule.get( license.key() );
                
            if ( sessionLicense != null )
            {
                sessionLicense.unlease();
                
                if ( sessionLicense.countLease() == 0 )
                {
                    sessionByModule.remove( license.key() );
                    
                    sessions.put( module, sessionByModule );
                    
                    ApplicationServices.getCurrent().getLicenseRepository().purge( license );
                }
            }
        }
    }
    
    /**
     * foward
     * 
     * @param e LicenseException
     */
    private void foward( LicenseException e )
    {
        try 
        {
            switch ( e.getType() )
            {
                case ERROR_LICENSE:
                    Executions.sendRedirect( "/error/license.jsp" );
                break;
                case INVALID_DATE:
                    Executions.sendRedirect( "/error/expired.jsp" );
                break;
                case MAX_SEAT:
                    Executions.sendRedirect( "/error/seat.jsp" );
                break;
                case MODULE_NOT_LICENSED:
                    Executions.sendRedirect( "/error/module.jsp" );
                break;

            }
        } 
        
        catch ( Exception ex )
        {
            ApplicationContext.getInstance().logException( ex );
        }
    }
    
    /**
     * session
     * 
     * @return String
     */
    private String session()
    {
        return session( Sessions.getCurrent() );
    }
    
    /**
     * session
     * 
     * @param session Session
     * @return String
     */
    private String session( Session session )
    {
        return HttpSession.class.cast( session.getNativeSession() ).getId();
    }
    
    /**
     * user
     * 
     * @return User
     */
    private User user()
    {
        return ApplicationContext.getInstance().getActiveUser();
    }
}