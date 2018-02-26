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
package com.me.eng.domain;

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
    private boolean onlyRootSamples;

    public Job getJob()
    {
        return job;
    }

    public void setJob( Job job )
    {
        this.job = job;
    }
    
    public Date getFrom()
    {
        return from;
    }

    public void setFrom( Date from )
    {
        this.from = from;
    }

    public Date getUntil()
    {
        return until;
    }

    public void setUntil( Date until )
    {
        this.until = until;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient( Client client )
    {
        this.client = client;
    }

    public boolean isOnlyRootSamples()
    {
        return onlyRootSamples;
    }

    public void setOnlyRootSamples( boolean onlyRootSamples )
    {
        this.onlyRootSamples = onlyRootSamples;
    }
    
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
        
        sql += " order by c.client, c.job, c.id";
        
        Query query = manager.createQuery( sql );
        
        if ( client != null && client != CLIENT_ALL )
        {
            query.setParameter( "ref_client", client );
        }
        
        if ( from != null )
        {
            query.setParameter( "from", from, TemporalType.DATE );
        }
        
        if ( until != null )
        {
            query.setParameter( "until", until, TemporalType.DATE );
        }
        
        if ( job != null )
        {
            query.setParameter( "job", job );
        }
        
        return query;
    }
}
