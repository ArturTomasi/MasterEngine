/* 
 *  Filename:    EquipmentDAO 
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

import com.me.eng.domain.Equipment;
import com.me.eng.domain.repositories.EquipmentRepository;
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
