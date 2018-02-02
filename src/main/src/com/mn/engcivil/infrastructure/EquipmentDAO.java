package com.mn.engcivil.infrastructure;

import com.mn.engcivil.domain.Equipment;
import com.mn.engcivil.domain.repositories.EquipmentRepository;
import javax.enterprise.inject.Default;

/**
 *
 * @author Matheus
 */
@Default
public class EquipmentDAO
    extends 
        EntityDAO<Equipment>
    implements 
        EquipmentRepository
{
    public EquipmentDAO()
    {
        super( Equipment.class );
    }

    @Override
    public int countSamples( Equipment e ) throws Exception
    {
        String sql = "select count(*) from eng_sample_equipments e" + 
                     " where e.ref_equipment = " + e.getId();
        
        return ( (Long) manager.createNativeQuery( sql ).getSingleResult() ).intValue();
    }
}
