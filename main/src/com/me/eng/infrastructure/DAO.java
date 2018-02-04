package com.me.eng.infrastructure;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Matheus
 */
public interface DAO<T>
    extends 
        Serializable
{
    public void add( T value );
    public void update( T value );
    public void delete( T value );
    public List<T> findAll();
}
