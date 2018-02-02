package com.mn.engcivil.domain.repositories;

import com.mn.engcivil.domain.Capstone;
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
