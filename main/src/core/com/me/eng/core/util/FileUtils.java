/* 
 *  Filename:    FileUtils 
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
package com.me.eng.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Matheus
 */
public class FileUtils
{
    /**
     * createTempFile
     * 
     * @param name String
     * @return File
     */
    public static File createTempFile( String name )
    {
        File tmp = new File( System.getProperty( "java.io.tmpdir" ), "temp" + System.currentTimeMillis() );
        tmp.mkdir();
        tmp.deleteOnExit();

        File out = new File( tmp, name );
        out.deleteOnExit();
        
        return out;
    }
    
    /**
     * delete
     * 
     * @param file File
     */
    public static void delete( File file )
    {
        if ( file.isDirectory() )
        {
            File children[] = file.listFiles();
            
            if ( children != null )
            {
                for ( File child : children )
                {
                    delete( child );
                }
            }
        }
        
        if ( ! file.delete() )
        {
            file.deleteOnExit();
        }
    }
    
    /**
     * zipFiles
     * 
     * @param files List&lt;File&gt;
     * @return File
     * @throws Exception
     */
    public static File zipFiles( List<File> files ) throws Exception
    {
        File out = createTempFile( "zip.zip" );
        
        FileOutputStream fos = new FileOutputStream( out );
        ZipOutputStream zos = new ZipOutputStream(fos);

        for ( File f : files )
        {
            addToZipFile( f, zos);
        }

        zos.close();
        fos.close();
        
        return out;
    }
    
    /**
     * addToZipFile
     * 
     * @param file File
     * @param zos ZipOutputStream
     * @throws FileNotFoundException, IOException
     */
    private static void addToZipFile( File file, ZipOutputStream zos ) throws FileNotFoundException, IOException
    {
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry( file.getName() );
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
    
        while ( ( length = fis.read( bytes ) ) >= 0 )
        {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        
        fis.close();
    }

}
