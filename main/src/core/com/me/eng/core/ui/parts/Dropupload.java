/*
 *  Filename:    DropUpload
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
package com.me.eng.core.ui.parts;

import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.domain.Attachment;
import com.me.eng.core.domain.AttachmentSource;
import com.me.eng.core.services.ApplicationServices;
import com.me.eng.core.ui.apps.Action;
import com.me.eng.core.ui.lists.AttachmentList;
import com.me.eng.core.util.FileUtils;
import java.util.List;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Html;

/**
 *
 * @author Artur Tomasi
 */
public class Dropupload 
    extends 
        Div
{
    private AttachmentSource source;
    
    /**
     * DropUpload
     * 
     */
    public Dropupload() 
    {
        initComponents();
    }

    /**
     * setSource
     * 
     * @param source AttachmentSource
     */
    public void setSource( AttachmentSource source ) 
    {
        try
        {
            this.source = source;

            list.setElements( ApplicationServices.getCurrent().getAttachmentRepository().find( source ) );

            updateLayout();
        }
        
        catch ( Exception e )
        {
            ApplicationContext.getInstance().handleException( e );
        }
    }
    
    /**
     * getAttachments
     * 
     * @return List&lt;Attachment&gt;
     */
    public List<Attachment> getAttachments() 
    {
        return list.getElements();
    }
    
    /**
     * doUpload
     * 
     * @param event Event
     */
    private void doUpload( Event event )
    {
        try
        {
            if ( event instanceof UploadEvent )
            {
                UploadEvent e = (UploadEvent) event;
            
                for ( Media media : e.getMedias() )
                {
                    Attachment attachment = new Attachment();
                    attachment.setFamily( source.getFamily() );
                    attachment.setSource( source.getId() );
                    attachment.setName( media.getName() );
                    attachment.setType( Attachment.Type.typeOf( media.getContentType() ) );
                    attachment.setFile( FileUtils.asFile( media ) );
                    
                    list.addElement( attachment );
                    
                    if ( source.getId() != null && source.getId() > 0 )
                    {
                        ApplicationServices.getCurrent().getAttachmentRepository().add( attachment );
                    }
                }
            }
            
            updateLayout();
        }
        
        catch ( Exception e )
        {
            ApplicationContext.getInstance().handleException( e );
        }
    }
    
    /**
     * delete
     * 
     * @throws Exception
     */
    private void delete() throws Exception
    {
        Attachment attachment = list.getSelectedElement();
        
        list.removeElement( attachment );
        
        if ( attachment.getId() != null && attachment.getId() > 0 )
        {
            ApplicationServices.getCurrent().getAttachmentRepository().delete( attachment );
        }
    }
    
    /**
     * updateLayout
     * 
     */
    private void updateLayout()
    {
        infoField.setVisible( list.getElements().isEmpty() );
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        setHflex( "true" );
        setVflex( "true" );
        
        setSclass( "dropupload" );

        dropupload.setVflex( "true" );
        dropupload.setHflex( "true" );
        
        dropupload.setDetection( "browser" );
        dropupload.setMaxsize( 3000000 );
        
        infoField.setContent( "<div class=\"dropupload-text\">Solte arquivos ou clique para fazer o upload.</div>" );
        
        list.addContextAction( deleteAction );
        list.addContextAction( downloadAction );
        list.addContextAction( uploadAction );
        
        appendChild( dropupload );
        appendChild( list );
        appendChild( infoField  );

        dropupload.addEventListener( Events.ON_UPLOAD, e -> doUpload( e ) );
    }
    
    private org.zkoss.zkmax.zul.Dropupload dropupload = new org.zkoss.zkmax.zul.Dropupload();
    private Html infoField = new Html();
    
    private AttachmentList list = new AttachmentList();
    
    private Action deleteAction = new Action( "core/tb_delete.png", "Excluir", "Excluir arquivo selecionado!" ) 
    {
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            delete();
        }
    };
    
    private Action downloadAction = new Action( "core/tb_download.png", "Download", "Baixar arquivo selecionado!" ) 
    {
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            Attachment a = list.getSelectedElement();
            
            if ( a != null )
            {
                Filedownload.save( a.getFile(), a.getType().contentType() );
            }
        }
    };
    
    private Action uploadAction = new Action( "core/tb_upload.png", "Upload", "Carregar arquivo!" ) 
    {
        {
            setUpload( "true,maxsize=3000000" );
        }
        
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            doUpload( t );
        }
    };
}
