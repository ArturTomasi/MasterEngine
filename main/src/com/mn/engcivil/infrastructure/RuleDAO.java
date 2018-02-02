package com.mn.engcivil.infrastructure;

import com.mn.engcivil.domain.Rule;
import com.mn.engcivil.domain.Sample;
import com.mn.engcivil.domain.repositories.RuleRepository;
import javax.enterprise.inject.Default;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Matheus
 */
@Default
public class RuleDAO
    extends 
        EntityDAO<Rule>
    implements 
        RuleRepository
{
    public RuleDAO()
    {
        super( Rule.class );
    }

    @Override
    public int countSamples( Rule rule ) throws Exception
    {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        
        CriteriaQuery<Long> query = builder.createQuery( Long.class );
        
        Root<Sample> root = query.from( Sample.class );
        
        return manager.createQuery( query
                .select( builder.count( root ) )
                .where( builder.and( 
                        builder.equal( root.get( "rule" ), rule ) ) ) ).getSingleResult().intValue();
    }
}
