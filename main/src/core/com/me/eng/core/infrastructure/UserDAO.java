/* 
 *  Filename:    UserDAO 
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
import com.me.eng.core.domain.User;
import com.me.eng.core.repositories.UserRepository;
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
public class UserDAO
    extends 
        EntityDAO<User>
    implements 
        UserRepository
{
    /**
     * UserDAO
     * 
     */
    public UserDAO()
    {
        super( User.class );
    }

    /**
     * findAll
     * 
     * @return List&lt;User&gt;
     */
    @Override
    public List<User> findAll()
    {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        
        CriteriaQuery query = criteriaBuilder.createQuery( User.class );
        
        Root root = query.from( User.class );
        
        query.select( root );
        
        ParameterExpression param = criteriaBuilder.parameter( Integer.class );
        
        query.where( criteriaBuilder.greaterThan( root.get( "id" ), param ) );
        
        TypedQuery typedQuery = manager.createQuery( query );
        
        typedQuery.setParameter( param, 0 );
        
        return typedQuery.getResultList();
    }

    /**
     * findByLogin
     * 
     * @param login String
     * @return User
     * @throws Exception
     */
    @Override
    public User findByLogin( String login ) throws Exception
    {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery( persistentClass );
        
        Root<User> root = criteriaQuery.from( persistentClass );
        
        criteriaQuery.select( root );
        
        ParameterExpression paramter = criteriaBuilder.parameter( String.class );
        
        criteriaQuery.where( criteriaBuilder.equal( root.get( "login" ), paramter ) );
        
        TypedQuery<User> typedQuery = manager.createQuery( criteriaQuery );
        typedQuery.setParameter( paramter, login );
        
        return Iterables.getFirst( typedQuery.getResultList(), null );
    }
}
