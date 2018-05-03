/* 
 *  Filename:    ConfigurationManager 
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

import com.me.eng.core.domain.ConfigurationNode;
import com.me.eng.core.domain.Security;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Time;
import java.util.Properties;
import org.zkoss.zk.ui.WebApps;

/**
 *
 * @author Matheus
 */
public class ConfigurationManager
{
    /**
     * getInstance
     * 
     * @return ConfigurationManager
     */
    public static ConfigurationManager getInstance()
    {
        return getInstance( WebApps.getCurrent().getResourceAsStream( "/WEB-INF/config.properties" ) );
    }
    
    /**
     * getInstance
     * 
     * @param in InputStream
     * @return ConfigurationManager
     */
    public static ConfigurationManager getInstance( InputStream in )
    {
        return new ConfigurationManager( in );
    }

    /**
     * clearSystemProperty
     * 
     * @param key String
     */
    public static void clearSystemProperty( String key )
    {
        System.clearProperty( key );
    }
    

    /**
     * setSystemProperty
     * 
     * @param key String
     * @param value String
     */
    public static void setSystemProperty( String key, String value )
    {
        System.setProperty( key, value );
    }
    
    /**
     * getSystemProperty
     * 
     * @param key String
     * @return String
     */
    public static String getSystemProperty( String key )
    {
        return System.getProperty( key );
    }
    
    
    private Properties properties = new Properties();
    
    /**
     * ConfigurationManager
     * 
     * @param in InputStream
     */
    public ConfigurationManager( InputStream in )
    {
        try
        {
            properties.load( in );
            
            in.close();
        }
        
        catch ( Exception e ) 
        {
            throw new RuntimeException( e );
        }
    }
    
    /**
     * getTokens
     * 
     * @param key String
     * @return String[]
     */
    public String[] getTokens( String key )
    {
        String value = getProperty( key );
        
        if ( value != null )
        {
            return value.split( "," );
        }
        
        return new String[]{};
    }
    
    /**
     * getTime
     * 
     * @param key String
     * @param defaultTime Time
     * @return Time
     */
    public Time getTime( String key, Time defaultTime )
    {
        String value = getProperty( key );
        
        if ( value != null )
        {
            return Time.valueOf( value );
        }
        
        return defaultTime;
    }
    
    /**
     * getInt
     * 
     * @param key String
     * @return int
     */
    public int getInt( String key )
    {
        return Integer.parseInt( getProperty( key, "0" ) );
    }
    
    /**
     * getFlag
     * 
     * @param key String
     * @return boolean
     */
    public boolean getFlag( String key )
    {
        return Boolean.parseBoolean( getProperty( key, "false" ) );
    }
    
    /**
     * getDecryptedProperty
     * 
     * @param key String
     * @return String
     */
    public String getDecryptedProperty( String key )
    {
        String value = getProperty( key );
        
        if ( value != null )
        {
            return Security.decrypt( value );
        }
        
        return null;
    }
    
    /**
     * getProperty
     * 
     * @param key String
     * @return String
     */
    public String getProperty( String key )
    {
        return getProperty( key, null );
    }
    
    /**
     * getProperty
     * 
     * @param key String
     * @param defaultValue String
     * @return String
     */
    public String getProperty( String key, String defaultValue )
    {
        return properties.getProperty( key, defaultValue );
    }
    
    /**
     * setProperty
     * 
     * @param key String
     * @param o Object
     */
    public void setProperty( String key, Object o )
    {
        properties.setProperty( key, o.toString() );
    }
    
    
    /**
     * store
     * 
     * @param node ConfigurationNode
     * @throws Exception
     */
    public void store( ConfigurationNode node ) throws Exception
    {
        node.store( properties );
        
        store();
    }
    
    /**
     * store
     * 
     * @throws Exception
     */
    public void store() throws Exception
    {
        store( new File( WebApps.getCurrent().getRealPath( "/WEB-INF/config.properties" ) ) );
    }
    
    /**
     * store
     * 
     * @param out File
     * @throws Exception
     */
    public void store( File out ) throws Exception
    {
        FileOutputStream fos = null;
        
        try
        {
            fos = new FileOutputStream( out );
            
            properties.store( fos, null );
        }
        
        finally
        {
            if ( fos != null )
            {
                fos.flush();
                fos.close();
            }
        }
    }
}
