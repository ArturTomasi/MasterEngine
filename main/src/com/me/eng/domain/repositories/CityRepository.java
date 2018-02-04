/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.me.eng.domain.repositories;

import com.me.eng.domain.Capstone;
import com.me.eng.domain.City;
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
