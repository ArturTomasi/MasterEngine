/* 
 *  Filename:    CityDAO 
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

import com.me.eng.core.domain.City;
import com.me.eng.core.repositories.CityRepository;
import javax.enterprise.inject.Default;

/**
 *
 * @author Matheus
 */
@Default
public class CityDAO
    extends 
        EntityDAO<City>
    implements 
        CityRepository
{
    /**
     * CityDAO
     * 
     */
    public CityDAO()
    {
        super( City.class );
    }
}
