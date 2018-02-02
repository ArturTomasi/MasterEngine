package com.mn.engcivil.domain.repositories;

import com.mn.engcivil.domain.Client;
import com.mn.engcivil.domain.Cnpj;
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
