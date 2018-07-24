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
     * from
     * 
     * @param date Date
     */
    public void from( Date date )
    {
        this.from = date;
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
     * info
     * 
     * @param info String
     */
    public void info( String info )
    {
        this.info = info;
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
     * state
     * 
     * @param state PostingState
     */
    public void state( PostingState state )
    {
        this.state = state;
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
                return manager.createQuery
                ( 
                    " select p from Posting p " +
                    " where " +
                    " p.owner = :user "
                );
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
