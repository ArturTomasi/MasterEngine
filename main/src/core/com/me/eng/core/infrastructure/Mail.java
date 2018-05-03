/* 
 *  Filename:    Mail 
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
package com.me.eng.core.infrastructure;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

/**
 *
 * @author Matheus
 */
public class Mail
    extends 
        Observable
{
    public enum Status
    {
        IDLE( "Parado", "core/ti_idle.png" ),
        PENDING( "Em espera", "core/ti_pending.png" ),
        SENDING( "Enviando", "core/ti_sending.gif" ),
        SUCCESS( "Sucesso", "core/ti_ok.png" ),
        FAIL( "Falha", "core/ti_error.png" ),
        UNKNOWN( "Desconhecido", "core/ti_unknown.png" );
        
        private String label;
        private String icon;

        /**
         * Status
         * 
         * @param label String
         * @param icon String
         */
        private Status( String label, String icon )
        {
            this.label = label;
            this.icon = icon;
        }

        /**
         * getLabel
         * 
         * @return String
         */
        public String getLabel()
        {
            return label;
        }

        /**
         * getIcon
         * 
         * @return String
         */
        public String getIcon()
        {
            return icon;
        }
    }
    
    private String subject;
    private String content;
    private Throwable exception;
    private List<File> attachments = new LinkedList<File>();
    private Collection<String> recipients = new LinkedHashSet<String>();
    
    private Status status = Status.IDLE;
    
    private File imageFooter;
    
    private Object source;

    /**
     * setSource
     * 
     * @param source Object
     */
    public void setSource( Object source )
    {
        this.source = source;
    }

    /**
     * getSource
     * 
     * @return Object
     */
    public Object getSource()
    {
        return source;
    }
    
    /**
     * setException
     * 
     * @param exception Throwable
     */
    public void setException( Throwable exception )
    {
        this.exception = exception;
    }

    /**
     * getException
     * 
     * @return Throwable
     */
    public Throwable getException()
    {
        return exception;
    }
    
    /**
     * setStatus
     * 
     * @param status Status
     */
    public void setStatus( Status status )
    {
        Status oldStatus = this.status;
        
        this.status = status;
        
        if ( ! Objects.equals( oldStatus, this.status ) )
        {
            setChanged();
            notifyObservers();
        }
    }

    /**
     * getStatus
     * 
     * @return Status
     */
    public Status getStatus()
    {
        return status;
    }

    /**
     * getSubject
     * 
     * @return String
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * setSubject
     * 
     * @param subject String
     */
    public void setSubject( String subject )
    {
        this.subject = subject;
    }
    
    /**
     * setImageFooter
     * 
     * @param file File
     */
    public void setImageFooter( File file )
    {
        this.imageFooter = file;
    }

    /**
     * getImageFooter
     * 
     * @return File
     */
    public File getImageFooter()
    {
        return imageFooter;
    }

    /**
     * getContent
     * 
     * @return String
     */
    public String getContent()
    {
        return content;
    }

    /**
     * setContent
     * 
     * @param content String
     */
    public void setContent( String content )
    {
        this.content = content;
    }

    /**
     * getAttachments
     * 
     * @return List&lt;File&gt;
     */
    public List<File> getAttachments()
    {
        return Collections.unmodifiableList( attachments );
    }

    /**
     * addAttachment
     * 
     * @param file File
     */
    public void addAttachment( File file )
    {
        attachments.add( file );
    }

    /**
     * addRecipient
     * 
     * @param email String...
     */
    public void addRecipient( String... email )
    {
        recipients.addAll( Arrays.asList( email ) );
    }
    
    /**
     * getRecipients
     * 
     * @return Collection&lt;String&gt;
     */
    public Collection<String> getRecipients()
    {
        return Collections.unmodifiableCollection( recipients );
    }
}
