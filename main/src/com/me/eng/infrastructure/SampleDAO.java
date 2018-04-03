/* 
 *  Filename:    SampleDAO 
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

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.me.eng.domain.Client;
import com.me.eng.domain.Job;
import com.me.eng.domain.Sample;
import com.me.eng.domain.SampleFilter;
import com.me.eng.domain.SampleFilterBuilder;
import com.me.eng.domain.SampleMail;
import com.me.eng.domain.repositories.SampleRepository;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

/**
 *
 * @author Matheus
 */
@Default
public class SampleDAO
    extends 
        EntityDAO<Sample>
    implements 
        SampleRepository
{
    public SampleDAO()
    {
        super( Sample.class );
    }
    
    public SampleDAO( EntityManager manager )
    {
        super( Sample.class, manager );
    }
    
    @Override
    public List<String> getDistinctNames()
    {
        return manager.createQuery( "select distinct c.name from " + 
                                       Sample.class.getCanonicalName() + " c " ).getResultList();
    }

    @Override
    public List<Sample> getSamples( Client client )
    {
        return getSamples( SampleFilterBuilder.newBuilder()
                               .withClient( client )
                               .build() );
    }
    
    @Override
    public List<Sample> getSamples( SampleFilter filter )
    {
        List<Sample> samples = filter.createQuery( manager ).getResultList();

        Job nullJob = new Job();
        nullJob.setId( 0 );
        
        samples.sort( new Comparator<Sample>()
        {
            @Override
            public int compare( Sample o1, Sample o2 )
            {
                int result = o1.getClient().getName()
                                    .compareToIgnoreCase( o2.getClient().getName() );
                
                if ( result != 0 ) return result;
                
                result = Objects.firstNonNull( o1.getJob(), nullJob ).getId()
                                    .compareTo( Objects.firstNonNull( o2.getJob(), nullJob ).getId() );
                
                if ( result != 0 ) return result;

                if ( o1.getParent() != null )
                {
                    if ( o2.getParent() != null )
                    {
                        result = o1.getParent().getId().compareTo( o2.getParent().getId() );

                        if ( result != 0 ) return result;
                    }
                    
                    else if ( ! o1.getParent().equals( o2 ) )
                    {
                        return -1;
                    }
                }
                
                else
                {
                    if ( o2.getParent() != null )
                    {
                        return 1;
                    }
                }
                
                return o1.getId().compareTo( o2.getId() );
            }
        } );
         
        return samples;
    }

    @Override
    public List<Sample> getSamples( Job job )
    {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Sample> criteriaQuery = criteriaBuilder.createQuery( persistentClass );
        
        Root<Sample> root = criteriaQuery.from( persistentClass );
        
        criteriaQuery.select( root );
        
        ParameterExpression paramter = criteriaBuilder.parameter( Job.class );
        
        criteriaQuery.where( criteriaBuilder.equal( root.get( "job" ), paramter ) );
        
        TypedQuery<Sample> typedQuery = manager.createQuery( criteriaQuery );
        typedQuery.setParameter( paramter, job );
        
        return typedQuery.getResultList();
    }

    @Override
    public List<Sample> getSamplesToNofitication() throws Exception
    {
        String sql = "select p from Sample p" + 
                     " where " + 
                     "(" + 
                        "p.notificationRuptureDate <= :date " + 
                        " and " + 
                        "p.dateRupture >= :date " + 
                     ")" + 
                     " or " + 
                    "p.dateRupture = :date";
                     
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        
        ParameterExpression<Date> param = builder.parameter( Date.class, "date" );
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis( System.currentTimeMillis() );
        calendar.set( Calendar.HOUR_OF_DAY, 23 );
        calendar.set( Calendar.MINUTE, 59 );
        calendar.set( Calendar.SECOND, 0 );
        calendar.set( Calendar.MILLISECOND, 0 );
        
        return manager.createQuery( sql )
                .setParameter( param, calendar.getTime(), TemporalType.DATE )
                .getResultList();
    }

    @Override
    public Sample getLastSample( Client c ) throws Exception
    {
        String sql = " select * from eng_samples s1 " +
                     " where " +
                     " s1.id = ( " +
                          " select " + 
                            " max( s2.id ) " + 
                          " from " +
                            " eng_samples s2  " +
                          " where " +
                            " ref_client = " + c.getId() +
                          " and " +
                            " ref_parent is null" +
                    " ) ";
              
        Query query = manager.createNativeQuery( sql, Sample.class );
        query.setMaxResults( 1 );
        
        return Iterables.getFirst( query.getResultList(), new Sample() );
    }

    @Override
    @Transactional
    public void addSampleMail( SampleMail sampleMail ) throws Exception
    {
        manager.persist( sampleMail );
    }

    @Override
    @Transactional
    public void updateSampleMail( SampleMail sampleMail ) throws Exception
    {
        manager.merge( sampleMail );
    }

    @Override
    public SampleMail getLastSampleMail( Sample sample ) throws Exception
    {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<SampleMail> criteriaQuery = criteriaBuilder.createQuery( SampleMail.class );
        
        Root<SampleMail> root = criteriaQuery.from( SampleMail.class );
        
        criteriaQuery.select( root );
        
        ParameterExpression paramter = criteriaBuilder.parameter( Sample.class );
        
        criteriaQuery.where( criteriaBuilder.equal( root.get( "sample" ), paramter ) );
        
        criteriaQuery.orderBy( criteriaBuilder.desc( root.get( "date" ) ) );
        
        TypedQuery<SampleMail> typedQuery = manager.createQuery( criteriaQuery );
        typedQuery.setParameter( paramter, sample );
        typedQuery.setMaxResults( 1 );
        
        return Iterables.getFirst( typedQuery.getResultList(), null );
    }
    
    @Override
    public boolean hasSuccessSampleMail( Sample sample ) throws Exception
    {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<SampleMail> query = builder.createQuery( SampleMail.class );
        
        Root<SampleMail> fromMail = query.from( SampleMail.class );
        
        TypedQuery<SampleMail> typedQuery = manager.createQuery( query
            .select( fromMail )
            .where( builder.and( 
                    builder.equal( fromMail.get( "sample" ), sample ),
                    builder.equal( fromMail.get( "status" ), Mail.Status.SUCCESS.ordinal() )
            ) ) )
        .setMaxResults( 1 );

        return typedQuery.getResultList().size() > 0;
    }
}
