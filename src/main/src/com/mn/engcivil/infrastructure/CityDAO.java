package com.mn.engcivil.infrastructure;

import com.mn.engcivil.domain.City;
import com.mn.engcivil.domain.repositories.CityRepository;
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
