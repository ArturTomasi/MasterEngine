package com.me.eng.domain.repositories;

import com.me.eng.domain.Client;
import com.me.eng.domain.Cnpj;
import javax.inject.Named;

/**
 *
 * @author Matheus
 */
@Named
public interface ClientRepository
    extends 
        EntityRepository<Client>
{
    public Client getClientByCNPJ( Cnpj cnpj ) throws Exception;
    
    public int countSamples( Client client );
}
