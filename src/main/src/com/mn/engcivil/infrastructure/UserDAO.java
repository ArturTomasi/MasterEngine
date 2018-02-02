package com.mn.engcivil.infrastructure;

import com.google.common.collect.Iterables;
import com.mn.engcivil.domain.User;
import com.mn.engcivil.domain.repositories.UserRepository;
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
    public UserDAO()
    {
        super( User.class );
    }

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
