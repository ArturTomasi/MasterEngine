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

    public EntityDAO( Class persistentClass )
    {
        this.persistentClass = persistentClass;
    }

    public EntityDAO( Class persistentClass, EntityManager manager )
    {
        this.persistentClass = persistentClass;
        this.manager = manager;
    }

    @Override
    @Transactional
    public void add( T value )
    {
        manager.persist( value );
    }

    @Override
    @Transactional
    public void update( T value )
    {
        manager.merge( value );
    }

    @Override
    @Transactional
    public void delete( T value )
    {
        manager.remove( manager.merge( value ) );
    }
    
    @Override
    public List<T> findAll()
    {
        return manager.createQuery( "select p from " + persistentClass.getSimpleName() + " p" ).getResultList();
    }
}
