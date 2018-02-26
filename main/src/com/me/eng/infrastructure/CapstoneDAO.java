/* 
 *  Filename:    CapstoneDAO 
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
package com.me.eng.infrastructure;

import com.me.eng.domain.Capstone;
import com.me.eng.domain.repositories.CapstoneRepository;
import javax.enterprise.inject.Default;

/**
 *
 * @author Matheus
 */
@Default
public class CapstoneDAO
    extends 
        EntityDAO<Capstone>
    implements 
        CapstoneRepository
{
    public CapstoneDAO()
    {
        super( Capstone.class );
    }

    @Override
    public int countSamples( Capstone capstone ) throws Exception
    {
        String sql = "select count(*) from eng_sample_capstones e" + 
                     " where e.ref_capstone = " + capstone.getId();
        
        return ( (Long) manager.createNativeQuery( sql ).getSingleResult() ).intValue();
    }
}
