/* 
 *  Filename:    EntityDAO 
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

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Matheus
 */
public class EntityDAO<T>
    implements 
        DAO<T>
{
    @Inject
    protected EntityManager manager;

    protected Class persistentClass;

    /**
     * EntityDAO
     * 
     * @param persistentClass Class
     */
    public EntityDAO( Class persistentClass )
    {
        this.persistentClass = persistentClass;
    }

    /**
     * EntityDAO
     * 
     * @param persistentClass Class
     * @param manager EntityManager
     */
    public EntityDAO( Class persistentClass, EntityManager manager )
    {
        this.persistentClass = persistentClass;
        this.manager = manager;
    }

    /**
     * add
     * 
     * @param value T
     */
    @Override
    @Transactional
    public void add( T value )
    {
        manager.persist( value );
    }

    /**
     * update
     * 
     * @param value T
     */
    @Override
    @Transactional
    public void update( T value )
    {
        manager.merge( value );
    }

    /**
     * delete
     * 
     * @param value T
     */
    @Override
    @Transactional
    public void delete( T value )
    {
        manager.remove( manager.merge( value ) );
    }
    
    /**
     * findAll
     * 
     * @return List&lt;T&gt;
     */
    @Override
    public List<T> findAll()
    {
        return manager.createQuery( "select p from " + persistentClass.getSimpleName() + " p" ).getResultList();
    }
}
