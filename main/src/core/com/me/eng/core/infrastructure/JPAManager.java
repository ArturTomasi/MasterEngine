/* 
 *  Filename:    JPAManager 
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
package com.me.eng.core.infrastructure;

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
    /**
     * createFactory
     * 
     * @return EntityManagerFactory
     */
    @Produces @ApplicationScoped
    public EntityManagerFactory createFactory()
    {
        return Persistence.createEntityManagerFactory( "default" );
    }
    
    /**
     * createManager
     * 
     * @param factory EntityManagerFactory
     * @return EntityManager
     */
    @Produces @RequestScoped
    public EntityManager createManager( EntityManagerFactory factory )
    {
        return factory.createEntityManager();
    }
    
    /**
     * release
     * 
     * @param EntityManager @Disposes
     * @param  manager
     */
    public void release( @Disposes EntityManager manager )
    {
        manager.close();
    }
}
