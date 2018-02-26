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
import com.me.eng.license.exceptions.LicenseException;
import com.me.eng.license.signature.LicenseConstants;
import com.me.eng.license.signature.LicenseValidator;
import com.me.eng.services.ApplicationServices;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.zkoss.zk.ui.Executions;
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
    
    private final Map<String, Integer> licensedModules = new HashMap();
    private final Map<String, License> sessions        = new HashMap();
    
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
                    throw new LicenseException( LicenseException.Type.INVALID_DATE );
                }
            
                if ( today.before( dateStart ) )
                {
                    throw new LicenseException( LicenseException.Type.INVALID_DATE );
                }

                values.forEach( (key, value) ->
                {
                    if ( key.startsWith( "max_eng" ) )
                    {
                        licensedModules.put( key.substring( 4 ), Integer.valueOf( value ) );
                    }
                } );
            }
        }
        
        catch ( LicenseException e ) 
        {
            ConfigurationManager.setSystemProperty( "license_exception", "true" );
        }
    }
    
    /**
     * clear
     * 
     */
    public void clear()
    {
        sessions.clear();
        
        licensedModules.clear();
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
                License sessionLicense = sessions.get( license.key() );

                if ( sessionLicense != null )
                {
                    sessionLicense.lease();

                    if ( max < sessionLicense.countLease() )
                    {
                        throw new LicenseException( LicenseException.Type.MAX_SEAT );
                    }

                    sessions.put( sessionLicense.key(), sessionLicense );

                }        

                else 
                {
                    license.lease();

                    sessions.put( license.key(), license );

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
            License sessionLicense = sessions.get( license.key() );
            
            if ( sessionLicense != null )
            {
                sessionLicense.unlease();
                
                if ( sessionLicense.countLease() == 0 )
                {
                    ApplicationServices.getCurrent().getLicenseRepository().delete( license );
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
        return HttpSession.class.cast( Sessions.getCurrent().getNativeSession() ).getId();
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