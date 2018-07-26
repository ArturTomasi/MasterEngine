/*
 *  Filename:    PostingFilter
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
package com.me.eng.finances.domain;

import com.me.eng.core.domain.User;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Artur Tomasi
 */
public class PostingFilter 
{
    public enum Mode
    {
        CUSTOM
        {
            @Override
            public String toString(){ return "Customizado"; }
        }, 
        
        PENDENCY
        {
            @Override
            public String toString(){ return "Pendentes"; }
        },
        
        ALL
        {
            @Override
            public String toString(){ return "Todos"; }
        }
    }
    
    private Date from, until;
    private String info;
    private User user;
    private PostingType type;
    private PostingState state;
    private Mode mode;

    /**
     * PostingFilter
     */
    public PostingFilter(){}
    
    /**
     * mode
     * 
     * @param mode Mode
     */
    public void mode( Mode mode )
    {
        this.mode = mode;
    }
  
    /**
     * mode
     * 
     * @return Mode
     */
    public Mode mode()
    {
        return this.mode;
    }
    
    /**
     * from
     * 
     * @param date Date
     */
    public void from( Date date )
    {
        this.from = date;
    }
    
    /**
     * from
     * 
     * @return Date
     */
    public Date from()
    {
        return this.from;
    }

    /**
     * until
     * 
     * @param date Date
     */
    public void until( Date date )
    {
        this.until = date;
    }
    
    /**
     * until
     * 
     * @return Date
     */
    public Date until()
    {
        return this.until;
    }

    /**
     * info
     * 
     * @param info String
     */
    public void info( String info )
    {
        this.info = info;
    }
        
    /**
     * info
     * 
     * @return Date
     */
    public String info()
    {
        return this.info;
    }

    /**
     * type
     * 
     * @param type PostingType
     */
    public void type( PostingType type )
    {
        this.type = type;
    }
    
    /**
     * type
     * 
     * @return Date
     */
    public PostingType type()
    {
        return this.type;
    }

    /**
     * state
     * 
     * @param state PostingState
     */
    public void state( PostingState state )
    {
        this.state = state;
    }
    
    /**
     * state
     * 
     * @return Date
     */
    public PostingState state()
    {
        return this.state;
    }
    
    /**
     * user
     * 
     * @param user User
     */
    public void user( User user )
    {
        this.user = user;
    }

    /**
     * user
     * 
     * @return User
     */
    public User user()
    {
        return this.user;
    }
    
    /**
     * toQuery
     * 
     * @param manager EntityManager
     * @return Query
     */
    public Query toQuery( EntityManager manager )
    {
        switch ( mode )
        {
            case ALL:
            {
                return manager.createQuery
                ( 
                    " select p from Posting p " +
                    " where " +
                    " p.owner = :user " +
                    " order by " +
                    " p.estimateDate "
                )
                .setParameter( "user",  user );
            }
            case PENDENCY:
            {
                return manager.createQuery
                ( 
                    " select p from Posting p " +
                    " where " +
                    " p.owner = :user " +
                    " and " +
                    " (" +
                        " p.state = :state " +
                        " or " +
                        " (" +
                            " func( 'month', p.estimateDate ) " +
                            " between " +
                            " func( 'month', current_date ) - 1 " +
                            " and " +
                            " func( 'month', current_date ) + 1 " +
                            " and " +
                            " p.state <> :finished " +
                        " )" +
                    " )" +
                    " order by " +
                    " p.estimateDate "
                )
                .setParameter( "state",    PostingState.PROGRESS )
                .setParameter( "finished", PostingState.FINISHED )
                .setParameter( "user",     user );
            }
            
            case CUSTOM:
            {
                String sql = " select p from Posting p " +
                             " where " +
                             " p.owner = :user ";
                
                if ( from != null )
                {
                    sql += " and " +
                           " coalesce( p.estimateDate, p.realDate  ) >= :from  ";
                }
                
                if ( until != null )
                {
                    sql += " and " +
                           " coalesce( p.estimateDate, p.realDate  ) <= :until  ";
                }
                
                if ( state != null )
                {
                    sql += " and " +
                           " p.state = :state ";
                }
                
                if ( type != null )
                {
                    sql += " and " +
                           " p.category.type = :type";
                }
                
                if ( info != null )
                {
                    sql += " and " +
                           " ( " +
                           " upper( p.name  ) like upper( :info ) " +
                           " or " +
                           " upper( p.info  ) like upper( :info ) " +
                           " ) ";
                              
                }
                
                Query q = manager.createQuery( sql )
                                  .setParameter( "user", user );
                
                if ( from != null )
                {
                    q.setParameter( "from", from );
                }
                
                if ( until != null )
                {
                    q.setParameter( "until", until );
                }
                
                if ( state != null )
                {
                    q.setParameter( "state", state );
                }
                
                if ( type != null )
                {
                    q.setParameter( "type", type );
                }
                
                if ( info != null )
                {
                    q.setParameter( "info", "%" + info + "%" );
                }
                
                return q;
            }
            
            default: 
            {
                return manager.createQuery
                ( 
                    "select p from Posting p"
                );
            }
        }
    }
}
