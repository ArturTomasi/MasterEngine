/*
 *  Filename:    CompanyDAO
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
 *  is the property of Over Line Ltda.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda,
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.finances.infrastructure;

import com.me.eng.core.infrastructure.EntityDAO;
import com.me.eng.finances.domain.Company;
import com.me.eng.finances.repositories.CompanyRepository;
import java.util.List;

/**
 *
 * @author Artur Tomasi
 */
public class CompanyDAO 
   extends 
        EntityDAO<Company>
            implements 
                CompanyRepository
{
    /**
     * CompanyDAO
     */
    public CompanyDAO()
    {
        super( Company.class );
    }
    
    /**
     * findAll
     * 
     * @return List&lt;Company&gt;
     */
    @Override
    public List<Company> findAll()
    {
        return manager.createQuery( "select p from " + persistentClass.getSimpleName() + " p" ).getResultList();
    }
}
