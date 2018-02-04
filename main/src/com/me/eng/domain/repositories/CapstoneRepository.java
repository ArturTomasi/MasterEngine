package com.me.eng.domain.repositories;

import com.me.eng.domain.Capstone;
import javax.inject.Named;

/**
 *
 * @author Matheus
 */
@Named
public interface CapstoneRepository
    extends 
        EntityRepository<Capstone>
{
    public int countSamples( Capstone capstone ) throws Exception;
}
