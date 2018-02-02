package com.mn.engcivil.infrastructure;

import com.google.common.collect.Iterables;
import com.mn.engcivil.domain.Client;
import com.mn.engcivil.domain.Cnpj;
import com.mn.engcivil.domain.Sample;
import com.mn.engcivil.domain.repositories.ClientRepository;
import java.util.List;
import javax.enterprise.inject.Default;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Matheus
 */
@Default
public class ClientDAO
    extends 
        EntityDAO<Client>
    implements 
        ClientRepository
{
    public ClientDAO()
    {
        super( Client.class );
    }

    @Override
    public int countSamples( Client client )
    {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery( Long.class );
        
        Root<Sample> fromMail = query.from( Sample.class );
        
        return manager.createQuery( query
                    .select( builder.count( fromMail ) )
                    .where( builder.and( builder.equal( fromMail.get( "client" ), client ) ) ) )
                    .getSingleResult().intValue();
    }

    @Override
    public List<Client> findAll()
    {
        return manager.createQuery( "select p from " + 
                                    persistentClass.getSimpleName() + 
                                    " p order by p.name" ).getResultList();
    }
    
    @Override
    public Client getClientByCNPJ( Cnpj cnpj ) throws Exception
    {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        
        CriteriaQuery<Client> query = builder.createQuery( Client.class );
        
        Root<Client> from = query.from( Client.class );
        
        TypedQuery<Client> typedQuery = 
                manager.createQuery( query
                             .select( from )
                             .where( builder.and( 
                                        builder.equal( from.get( "cnpj" ), cnpj ) 
                                    ) 
                             ) 
                );
        
        return Iterables.getFirst( typedQuery.getResultList(), null );
    }
}
