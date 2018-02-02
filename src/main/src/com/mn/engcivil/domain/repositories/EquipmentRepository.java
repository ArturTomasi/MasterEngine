package com.mn.engcivil.domain.repositories;

import com.mn.engcivil.domain.Equipment;
import javax.inject.Named;

/**
 *
 * @author Matheus
 */
@Named
public interface EquipmentRepository
    extends 
        EntityRepository<Equipment>
{
    public int countSamples( Equipment e ) throws Exception;
}
