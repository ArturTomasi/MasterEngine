/*
 * Filename:    SectorDAO 
 *
 * Author:      Artur Tomasi
 * EMail:       tomasi.artur@gmail.com
 * Internet:    https://www.masterengine.com.br
 *
 * Copyright © 2019 by Over Line Ltda.
 * 95900-038, LAJEADO, RS
 * BRAZIL
 *
 * The copyright to the computer program(s) herein
 * is the property of Over Line Ltda., Brazil.
 * The program(s) may be used and/or copied only with
 * the written permission of Over Line Ltda.
 * or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which
 * the program(s) have been supplied.
 */
package com.me.eng.core.infrastructure;

import com.google.common.collect.Iterables;
import com.me.eng.core.domain.Sector;
import com.me.eng.core.repositories.SectorRepository;
import javax.enterprise.inject.Default;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

/**
 *
 * @author Artur Tomasi
 */
@Default
public class SectorDAO 
    extends 
        EntityDAO<Sector>
    implements 
        SectorRepository
{
    /**
     * SectorDAO
     * 
     */
    public SectorDAO()
    {
        super( Sector.class );
    }

    /**
     * findByName
     * 
     * @param name String
     * @return Sector
     * @throws Exception
     */
    @Override
    public Sector findByName( String name ) throws Exception 
    {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<Sector> criteriaQuery = criteriaBuilder.createQuery( persistentClass );
        
        Root<Sector> root = criteriaQuery.from( persistentClass );
        
        criteriaQuery.select( root );
        
        ParameterExpression paramter = criteriaBuilder.parameter( String.class );
        
        criteriaQuery.where( criteriaBuilder.equal( root.get( "name" ), paramter ) );
        
        TypedQuery<Sector> typedQuery = manager.createQuery( criteriaQuery );
        typedQuery.setParameter( paramter, name );
        
        return Iterables.getFirst( typedQuery.getResultList(), null );
    }
}
