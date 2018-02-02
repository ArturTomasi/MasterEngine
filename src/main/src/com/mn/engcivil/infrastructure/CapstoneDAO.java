/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mn.engcivil.infrastructure;

import com.mn.engcivil.domain.Capstone;
import com.mn.engcivil.domain.repositories.CapstoneRepository;
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
