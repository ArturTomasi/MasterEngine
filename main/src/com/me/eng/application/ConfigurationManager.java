package com.me.eng.application;

import com.me.eng.domain.ConfigurationNode;
import com.me.eng.domain.Security;
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
    public static ConfigurationManager getInstance()
    {
        return getInstance( WebApps.getCurrent().getResourceAsStream( "/WEB-INF/config.properties" ) );
    }
    
    public static ConfigurationManager getInstance( InputStream in )
    {
        return new ConfigurationManager( in );
    }

    private Properties properties = new Properties();
    
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
    
    public String[] getTokens( String key )
    {
        String value = getProperty( key );
        
        if ( value != null )
        {
            return value.split( "," );
        }
        
        return new String[]{};
    }
    
    public Time getTime( String key, Time defaultTime )
    {
        String value = getProperty( key );
        
        if ( value != null )
        {
            return Time.valueOf( value );
        }
        
        return defaultTime;
    }
    
    public int getInt( String key )
    {
        return Integer.parseInt( getProperty( key, "0" ) );
    }
    
    public boolean getFlag( String key )
    {
        return Boolean.parseBoolean( getProperty( key, "false" ) );
    }
    
    public String getDecryptedProperty( String key )
    {
        String value = getProperty( key );
        
        if ( value != null )
        {
            return Security.decrypt( value );
        }
        
        return null;
    }
    
    public String getProperty( String key )
    {
        return getProperty( key, null );
    }
    
    public String getProperty( String key, String defaultValue )
    {
        return properties.getProperty( key, defaultValue );
    }
    
    public void setProperty( String key, Object o )
    {
        properties.setProperty( key, o.toString() );
    }
    
    public void store( ConfigurationNode node ) throws Exception
    {
        node.store( properties );
        
        store();
    }
    
    public void store() throws Exception
    {
        store( new File( WebApps.getCurrent().getRealPath( "/WEB-INF/config.properties" ) ) );
    }
    
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
