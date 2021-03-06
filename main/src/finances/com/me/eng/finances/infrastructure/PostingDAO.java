/*
 *  Filename:    PostingDAO
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
package com.me.eng.finances.infrastructure;

import com.google.common.collect.Iterables;
import com.me.eng.core.data.ChartData;
import com.me.eng.core.domain.User;
import com.me.eng.core.infrastructure.EntityDAO;
import com.me.eng.core.infrastructure.Transactional;
import com.me.eng.finances.domain.Posting;
import com.me.eng.finances.domain.PostingFilter;
import com.me.eng.finances.domain.PostingState;
import com.me.eng.finances.domain.PostingType;
import com.me.eng.finances.repositories.PostingRepository;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Artur Tomasi
 */
public class PostingDAO
   extends 
        EntityDAO<Posting>
            implements 
                PostingRepository
{
    /**
     * PostingDAO
     */
    public PostingDAO()
    {
        super( Posting.class );
    }
    
    /**
     * findAll
     * 
     * @return List&lt;User&gt;
     */
    @Override
    public List<Posting> findAll()
    {
        return manager.createQuery( "select p from " + persistentClass.getSimpleName() + " p " ).getResultList();
    }

    /**
     * find
     * 
     * @param filter PostingFilter
     * @return List&lt;Posting&gt;
     * @throws Exception
     */
    @Override
    public List<Posting> find( PostingFilter filter ) throws Exception 
    {
        return filter.toQuery( manager ).getResultList();
    }

    /**
     * getLastPosting
     * 
     * @param current Posting
     * @return Posting
     * @throws Exception
     */
    @Override
    public Posting getLastPosting( Posting current ) throws Exception 
    {
        TypedQuery<Posting> query = manager.createQuery( " select P from " + persistentClass.getSimpleName() + " P where " +
                                            " P.state <> :state " +
                                            " and ( "   +
                                               " case when " + 
                                               " P.portion = 2 " +
                                               " then " +
                                               " P.id = :parent" +
                                               " else " +
                                               " P.parent = :parent " +
                                               " and " +
                                               " P.portion = :portion " + 
                                               " end )", Posting.class );
        
        query.setParameter( "state",    current.getState() );
        query.setParameter( "parent",   current.getParent() );
        query.setParameter( "portion",  current.getPortion() - 1 );
        
        query.setMaxResults( 1 );
        
        return Iterables.getFirst( query.getResultList(), null);
    }

    /**
     * updateNextPortion
     * 
     * @param posting Posting
     * @throws Exception
     */
    @Override
    @Transactional
    public void updateNextPortion( Posting posting ) throws Exception 
    {
        if( posting == null )
        {
            throw new IllegalArgumentException( "Posting cannot be null" );
        }
        
        Query query = manager.createQuery( "update " +
                                           persistentClass.getSimpleName() +
                                           " set " + 
                                           " state     = :newstate , "     +
                                           " realDate  = :realDate , "  +
                                           " realValue = :realValue "   +
                                           " where " +
                                           " parent    = :parent " +
                                           " and " +
                                           " state     = :state " +
                                           " and " +
                                           " portion   = :portion " );
        
        query.setParameter( "state",     posting.getState() );
        query.setParameter( "newstate",  posting.getState() == PostingState.FINISHED ? PostingState.PROGRESS : PostingState.REGISTRED );
        query.setParameter( "realDate",  null );
        query.setParameter( "realValue", null );
        query.setParameter( "parent",    posting.getPortion() == 1 ? posting : posting.getParent() );
        query.setParameter( "portion",   posting.getPortion() + 1 );
        
        query.executeUpdate();
    }

    /**
     * deleteNextPortions
     * 
     * @param posting Posting
     * @throws Exception
     */
    @Override
    @Transactional
    public void deleteNextPortions(Posting posting) throws Exception 
    {
        if( posting == null )
        {
            throw new IllegalArgumentException( "Posting cannot be null" );
        }
        
        Query query = manager.createQuery( "delete from " +
                                           persistentClass.getSimpleName() +
                                           " where " +
                                           " parent    = :parent " +
                                           " and " +
                                           " portion   > :portion ",  persistentClass.getClass() );
        
        query.setParameter( "parent",    posting.getPortion() == 1 ? posting : posting.getParent() );
        query.setParameter( "portion",   posting.getPortion() );
        
        query.executeUpdate();
    }

    /**
     * sum
     * 
     * @return List&lt;ChartData&gt;
     */
    @Override
    public List<ChartData> sum()
    {
        String sql = " select " +
                     " func( 'month', P.realDate ), P.category.type, sum( P.realValue ) " +
                     " from " + 
                     persistentClass.getSimpleName() + " P " +
                     " where " +
                     " P.state = :state "+
                     " group by " +
                     " func( 'month', P.realDate ), P.category.type " +
                     " order by " +
                     " p.category.type";
        
        TypedQuery<Object[]> query = manager.createQuery( sql, Object[].class );
        
        query.setParameter( "state", PostingState.FINISHED );
        
        List<ChartData> data = new LinkedList();
        
        query.getResultList().forEach( o ->
        {
            ChartData d = new ChartData();
            d.put( ChartData.ATTR_SERIES,   o[0] );
            d.put( ChartData.ATTR_CATEGORY, o[1] );
            d.put( ChartData.ATTR_VALUE,    o[2] );
            data.add( d );
        });
        
        return data;
    }
}
