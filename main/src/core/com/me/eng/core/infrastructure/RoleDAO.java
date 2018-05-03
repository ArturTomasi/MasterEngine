/* 
 *  Filename:    RoleDAO 
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
package com.me.eng.core.infrastructure;

import com.google.common.collect.Iterables;
import com.me.eng.core.domain.Role;
import com.me.eng.core.repositories.RoleRepository;
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
public class RoleDAO
    extends 
        EntityDAO<Role>
    implements 
        RoleRepository
{
    /**
     * RoleDAO
     * 
     */
    public RoleDAO()
    {
        super( Role.class );
    }

    /**
     * getAdministratorRole
     * 
     * @return Role
     * @throws Exception
     */
    @Override
    public Role getAdministratorRole() throws Exception
    {
        return getRole( "administrator" );
    }

    /**
     * getRole
     * 
     * @param rolename String
     * @return Role
     * @throws Exception
     */
    @Override
    public Role getRole( String rolename ) throws Exception
    {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        
        CriteriaQuery criteriaQuery = builder.createQuery( Role.class );
        
        Root root = criteriaQuery.from( Role.class );
        
        ParameterExpression param = builder.parameter( String.class );
        
        criteriaQuery.select( root )
                     .where( builder.equal( root.get( "role" ), param ) );
        
        TypedQuery<Role> query = manager.createQuery( criteriaQuery )
               .setParameter( param, rolename );
        
        return Iterables.getFirst( query.getResultList(), null );
    }

    /**
     * findAll
     * 
     * @return List&lt;Role&gt;
     */
    @Override
    public List<Role> findAll()
    {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        
        CriteriaQuery query = criteriaBuilder.createQuery( Role.class );
        
        Root root = query.from( Role.class );
        
        query.select( root );
        
        ParameterExpression param = criteriaBuilder.parameter( Integer.class );
        
        query.where( criteriaBuilder.greaterThan( root.get( "id" ), param ) );
        
        TypedQuery typedQuery = manager.createQuery( query );
        
        typedQuery.setParameter( param, 0 );
        
        return typedQuery.getResultList();
    }
}
