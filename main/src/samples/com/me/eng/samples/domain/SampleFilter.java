/* 
 *  Filename:    SampleFilter 
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
package com.me.eng.samples.domain;

import com.me.eng.core.domain.Client;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author Matheus
 */
public class SampleFilter
{
    public static final Client CLIENT_ALL = new Client( "<TODOS>" );
    
    private Client client = CLIENT_ALL;
    private Job job;
    private Date from;
    private Date until;
    private String trace;
    private boolean onlyRootSamples;

    /**
     * getJob
     * 
     * @return Job
     */
    public Job getJob()
    {
        return job;
    }

    /**
     * setJob
     * 
     * @param job Job
     */
    public void setJob( Job job )
    {
        this.job = job;
    }
    
    /**
     * getFrom
     * 
     * @return Date
     */
    public Date getFrom()
    {
        return from;
    }

    /**
     * setFrom
     * 
     * @param from Date
     */
    public void setFrom( Date from )
    {
        this.from = from;
    }

    /**
     * getUntil
     * 
     * @return Date
     */
    public Date getUntil()
    {
        return until;
    }

    /**
     * setUntil
     * 
     * @param until Date
     */
    public void setUntil( Date until )
    {
        this.until = until;
    }

    /**
     * getClient
     * 
     * @return Client
     */
    public Client getClient()
    {
        return client;
    }

    /**
     * setClient
     * 
     * @param client Client
     */
    public void setClient( Client client )
    {
        this.client = client;
    }

    /**
     * isOnlyRootSamples
     * 
     * @return boolean
     */
    public boolean isOnlyRootSamples()
    {
        return onlyRootSamples;
    }

    /**
     * setOnlyRootSamples
     * 
     * @param onlyRootSamples boolean
     */
    public void setOnlyRootSamples( boolean onlyRootSamples )
    {
        this.onlyRootSamples = onlyRootSamples;
    }

    /**
     * getTrace
     * 
     * @return String
     */
    public String getTrace() 
    {
        return trace;
    }

    /**
     * setTrace
     * 
     * @param trace String
     */
    public void setTrace( String trace ) 
    {
        this.trace = trace;
    }
    
    /**
     * createQuery
     * 
     * @param manager EntityManager
     * @return Query
     */
    public Query createQuery( EntityManager manager )
    {
        String sql = "select c from " + Sample.class.getCanonicalName() + " c " + 
                     " where c.id <> 0";
        
        if ( client != null && client != CLIENT_ALL )
        {
            sql += " and " + 
                   "c.client = :ref_client";
        }
        
        if ( onlyRootSamples )
        {
            sql += " and " + 
                   "c.parent is null";
        }
        
        if ( from != null )
        {
            sql += " and " + 
                   "c.dateRupture >= :from";
        }
        
        if ( until != null )
        {
            sql += " and " + 
                   "c.dateRupture <= :until";
        }
        
        if ( job != null )
        {
            sql += " and " + 
                   "c.job = :job";
        }
        
        if ( trace != null )
        {
            sql += " and " +
                   "upper( c.trace ) like upper( :trace )";
        }
        
        sql += " order by c.client, c.job, c.dateRupture";
        
        Query query = manager.createQuery( sql );
        
        if ( client != null && client != CLIENT_ALL )
        {
            query.setParameter( "ref_client", client );
        }
        
        if ( from != null )
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime( from );
            calendar.set( Calendar.HOUR_OF_DAY, 0 );
            calendar.set( Calendar.MINUTE, 0 );
            calendar.set( Calendar.SECOND, 0 );
            calendar.set( Calendar.MILLISECOND, 0 );
            query.setParameter( "from", calendar.getTime(), TemporalType.TIMESTAMP );
        }
        
        if ( until != null )
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime( until );
            calendar.set( Calendar.HOUR_OF_DAY, 23 );
            calendar.set( Calendar.MINUTE, 59 );
            calendar.set( Calendar.SECOND, 0 );
            calendar.set( Calendar.MILLISECOND, 0 );
            
            query.setParameter( "until", calendar.getTime(), TemporalType.TIMESTAMP );
        }
        
        if ( job != null )
        {
            query.setParameter( "job", job );
        }
        
        if ( trace != null )
        {
            trace = trace.replaceAll( "[^0-9]", "" );

            query.setParameter( "trace", "%" + trace + "%" );
        }
        
        return query;
    }
}
