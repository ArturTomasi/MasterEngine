/* 
 *  Filename:    ResourceLocator 
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
package com.me.eng.application;

import java.io.InputStream;
import java.net.URL;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Matheus
 */
public class ResourceLocator
{
    /**
     * getImageURL
     * 
     * @param path String
     * @return URL
     */
    public static URL getImageURL( String path )
    {
        if ( ! path.startsWith( "/images" ) )
        {
            path = "/images/" + path;
        }
        
        return ResourceLocator.class.getResource( path );
    }
    
    /**
     * getImageResource
     * 
     * @param path String
     * @return String
     */
    public static String getImageResource( String path )
    {
        if ( ! path.startsWith( "/img" ) )
        {
            path = "/img/" + path;
        }
        
        return path;
    }
    
    /**
     * getResourceAsArray
     * 
     * @param path String
     * @return byte[]
     * @throws Exception
     */
    public static byte[] getResourceAsArray( String path ) throws Exception
    {
        return IOUtils.toByteArray( getResourceAsStream( path ) );
    }
    
    /**
     * getSharedFile
     * 
     * @param name String
     * @return String
     */
    public static String getSharedFile( String name )
    {
        String home = ConfigurationManager.getInstance().getProperty( "master_engine.home", "/master_engine/" );
        
        if ( ! home.endsWith( "/" ) )
        {
            home += "/";
        }
        
        return home + name;
    }
    
    /**
     * getResourceAsArray
     * 
     * @param path String
     * @return byte[]
     */
    public static URL getResourceAsURL( String path )
    {
        URL url = null;
    
        try
        {
            url = ResourceLocator.class.getResource( path );
        }
        
        catch ( Exception e )
        {
            ApplicationContext.getInstance().handleException( e );
        }
        
        return url;
    }
    
    /**
     * getResourceAsStream
     * 
     * @param path String
     * @return InputStream
     * @throws Exception
     */
    public static InputStream getResourceAsStream( String path ) throws Exception
    {
        return ResourceLocator.class.getResourceAsStream( path );
    }
}
