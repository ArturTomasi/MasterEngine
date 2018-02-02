/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mn.engcivil.domain.repositories;

import com.mn.engcivil.domain.Capstone;
import com.mn.engcivil.domain.City;
import javax.inject.Named;

/**
 *
 * @author Matheus
 */
@Named
public interface CityRepository
    extends 
        EntityRepository<City>
{
}
