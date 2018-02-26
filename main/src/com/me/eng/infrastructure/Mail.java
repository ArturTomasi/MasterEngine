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
package com.me.eng.infrastructure;

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
        IDLE( "Parado", "ti_idle.png" ),
        PENDING( "Em espera", "ti_pending.png" ),
        SENDING( "Enviando", "ti_sending.gif" ),
        SUCCESS( "Sucesso", "ti_ok.png" ),
        FAIL( "Falha", "ti_error.png" ),
        UNKNOWN( "Desconhecido", "ti_unknown.png" );
        
        private String label;
        private String icon;

        private Status( String label, String icon )
        {
            this.label = label;
            this.icon = icon;
        }

        public String getLabel()
        {
            return label;
        }

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

    public void setSource( Object source )
    {
        this.source = source;
    }

    public Object getSource()
    {
        return source;
    }
    
    public void setException( Throwable exception )
    {
        this.exception = exception;
    }

    public Throwable getException()
    {
        return exception;
    }
    
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

    public Status getStatus()
    {
        return status;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject( String subject )
    {
        this.subject = subject;
    }
    
    public void setImageFooter( File file )
    {
        this.imageFooter = file;
    }

    public File getImageFooter()
    {
        return imageFooter;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent( String content )
    {
        this.content = content;
    }

    public List<File> getAttachments()
    {
        return Collections.unmodifiableList( attachments );
    }

    public void addAttachment( File file )
    {
        attachments.add( file );
    }

    public void addRecipient( String... email )
    {
        recipients.addAll( Arrays.asList( email ) );
    }
    
    public Collection<String> getRecipients()
    {
        return Collections.unmodifiableCollection( recipients );
    }
}
