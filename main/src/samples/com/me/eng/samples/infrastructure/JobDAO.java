/* 
 *  Filename:    JobDAO 
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
package com.me.eng.samples.infrastructure;

import com.me.eng.core.infrastructure.EntityDAO;
import com.me.eng.core.domain.Client;
import com.me.eng.samples.domain.Job;
import com.me.eng.samples.domain.Sample;
import com.me.eng.samples.repositories.JobRepository;
import java.util.List;
import javax.enterprise.inject.Default;
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
public class JobDAO
    extends 
        EntityDAO<Job>
    implements 
        JobRepository
{
    public JobDAO()
    {
        super( Job.class );
    }

    @Override
    public List<Job> getJobs( Client client ) throws Exception
    {
        if ( ! Client.isPersisted( client ) )
        {
            return findAll();
        }
        
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        
        CriteriaQuery criteriaQuery = builder.createQuery( Job.class );
        
        Root root = criteriaQuery.from( Job.class );
        
        ParameterExpression param = builder.parameter( String.class );
        
        criteriaQuery.select( root )
                     .where( builder.equal( root.get( "client" ), param ) );
        
        TypedQuery<Job> query = manager.createQuery( criteriaQuery )
               .setParameter( param, client );
        
        return query.getResultList();
    }

    @Override
    public int countSamples( Job job ) throws Exception
    {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        
        CriteriaQuery<Long> query = builder.createQuery( Long.class );
        
        Root<Sample> root = query.from( Sample.class );
        
        return manager.createQuery( query
                .select( builder.count( root ) )
                .where( builder.and(
                        builder.equal( root.get( "job" ), job ) ) ) ).getSingleResult().intValue();
    }
}
