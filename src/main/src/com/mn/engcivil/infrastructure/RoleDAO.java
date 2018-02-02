package com.mn.engcivil.infrastructure;

import com.google.common.collect.Iterables;
import com.mn.engcivil.domain.Role;
import com.mn.engcivil.domain.repositories.RoleRepository;
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
    public RoleDAO()
    {
        super( Role.class );
    }

    @Override
    public Role getAdministratorRole() throws Exception
    {
        return getRole( "administrator" );
    }

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

    @Override
    public List<Role> findAll()
    {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        
        CriteriaQuery<Role> query = builder.createQuery( Role.class );
        
        Root<Role> root = query.from( Role.class );
        
        TypedQuery<Role> typedQuery = manager.createQuery( query
                .select( root )
                .where( builder.greaterThan( root.get( "id" ), 0 ) ) );
        
        return typedQuery.getResultList();
    }
}
