package com.mn.engcivil.application;

import java.io.InputStream;
import java.net.URL;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Matheus
 */
public class ResourceLocator
{
    public static URL getImageURL( String path )
    {
        if ( ! path.startsWith( "/images" ) )
        {
            path = "/images/" + path;
        }
        
        return ResourceLocator.class.getResource( path );
    }
    
    public static String getImageResource( String path )
    {
        if ( ! path.startsWith( "/img" ) )
        {
            path = "/img/" + path;
        }
        
        return path;
    }
    
    public static byte[] getResourceAsArray( String path ) throws Exception
    {
        return IOUtils.toByteArray( getResourceAsStream( path ) );
    }
    
    public static InputStream getResourceAsStream( String path ) throws Exception
    {
        return ResourceLocator.class.getResourceAsStream( path );
    }
}
