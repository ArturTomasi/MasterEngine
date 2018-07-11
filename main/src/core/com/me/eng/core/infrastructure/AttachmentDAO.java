/*
 *  Filename:    AttachmentDAO
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
 *  is the property of Over Line Ltda.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda,
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.core.infrastructure;

import com.me.eng.core.application.ConfigurationManager;
import com.me.eng.core.domain.Attachment;
import com.me.eng.core.domain.AttachmentSource;
import com.me.eng.core.repositories.AttachmentRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.persistence.Query;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Artur Tomasi
 */
public class AttachmentDAO 
    extends 
        EntityDAO<Attachment>
    implements 
        AttachmentRepository
{
    /**
     * AttachmentDAO
     * 
     */
    public AttachmentDAO() 
    {
        super( Attachment.class );
    }

    /**
     * add
     * 
     * @param value Attachment
     */
    @Override
    @Transactional
    public void add( Attachment value )
    {
        try
        {
            manager.persist( value );

            File file = toFile( value );
            
            if ( ! file.getParentFile().exists() )
            {
                file.mkdirs();
            }
            
            Files.copy( value.getFile().toPath(), 
                        file.toPath(), 
                        StandardCopyOption.REPLACE_EXISTING );
        }
        
        catch ( IOException e )
        {
            e.printStackTrace( System.err );
        }
    }

    /**
     * delete
     * 
     * @param value Attachment
     */
    @Override
    @Transactional
    public void delete( Attachment value )
    {
        manager.remove( manager.merge( value ) );
        
        try
        {
            File file = toFile( value );

            if ( file.getParentFile().listFiles().length == 1 )
            {
                FileUtils.deleteDirectory( dir( value.getFamily(), value.getSource() ) );
            }

            else
            {
                file.delete();
            }
        }
        
        catch ( IOException e )
        {
            e.printStackTrace( System.err );
        }
    }

    
    /**
     * delete
     * 
     * @param source AttachmentSource
     * @throws Exception
     */
    @Override
    @Transactional
    public void delete( AttachmentSource source ) throws Exception 
    {
        FileUtils.deleteDirectory( dir( source.getFamily(), source.getId() ) );
        
        Query query = manager.createQuery( " delete from " + 
                                           persistentClass.getSimpleName() +
                                           " where " +
                                           " family = :family " +
                                           " and " +
                                           " source = :source" );
        
        query.setParameter( "family", source.getFamily() );
        query.setParameter( "source", source.getId() );
        
        query.executeUpdate();
    }
    
    /**
     * findAll
     * 
     * @return List&lt;Attachment&gt;
     */
    @Override
    public List<Attachment> findAll() 
    {
        return wrap( super.findAll() );
    }
    
    /**
     * find
     * 
     * @param source AttachmentSource
     * @return List&lt;Attachment&gt;
     */
    @Override
    public List<Attachment> find( AttachmentSource source ) 
    {
        Query query = manager.createQuery( " select a from " + 
                                           persistentClass.getSimpleName() + " a" +
                                           " where " +
                                           " a.family = :family " +
                                           " and " +
                                           " a.source = :source" );
        
        query.setParameter( "family", source.getFamily() );
        query.setParameter( "source", source.getId() );
        
        return wrap( query.getResultList() );
    }

    /**
     * wrap
     * 
     * @param attachments List&lt;Attachment&gt;
     * @return List&lt;Attachment&gt;
     */
    private List<Attachment> wrap( List<Attachment> attachments )
    {
        attachments.forEach( a -> a.setFile( toFile( a ) ) );
        
        return attachments;
    }
    
    /**
     * toFile
     * 
     * @param attachment Attachment
     * @return File
     */
    private File toFile( Attachment attachment )
    {
        return new File( dir( attachment.getFamily(), attachment.getSource() ), attachment.getName() );
    }
    
    /**
     * dir
     * 
     * @param family Attachment.Family
     * @param source int
     * @return File
     */
    private File dir( Attachment.Family family, int source )
    {
        String home = ConfigurationManager.getInstance().getProperty( "master_engine.home" );
        
        String file = File.separator + home + File.separator + "attachments"  + File.separator + 
                      Integer.toHexString( family.ordinal() ) + File.separator + 
                      Integer.toHexString( source )           + File.separator ;
        
        return new File( file );
    }
}
