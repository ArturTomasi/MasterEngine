/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.me.eng.domain.repositories;

import java.util.List;

/**
 *
 * @author Matheus
 */
public interface EntityRepository<T>
{
    public void add( T u ) throws Exception;
    public void update( T u ) throws Exception;
    public void delete( T u ) throws Exception;
    public List<T> findAll() throws Exception;
}
