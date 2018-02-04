package com.me.eng.infrastructure;

import com.me.eng.domain.City;
import com.me.eng.domain.repositories.CityRepository;
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
    public CityDAO()
    {
        super( City.class );
    }
}
