package com.mn.engcivil.domain;

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
