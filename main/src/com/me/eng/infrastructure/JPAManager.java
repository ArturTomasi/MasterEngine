package com.me.eng.infrastructure;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Matheus
 */
public class JPAManager
{
    @Produces @ApplicationScoped
    public EntityManagerFactory createFactory()
    {
        return Persistence.createEntityManagerFactory( "default" );
    }
    
    @Produces @RequestScoped
    public EntityManager createManager( EntityManagerFactory factory )
    {
        return factory.createEntityManager();
    }
    
    public void release( @Disposes EntityManager manager )
    {
        manager.close();
    }
}
