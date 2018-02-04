package com.me.eng.infrastructure;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

/**
 *
 * @author Matheus
 */
@Interceptor
@Transactional
public class TransactionalManager
{
    @Inject EntityManager manager;
    
    @AroundInvoke
    public Object intercept( InvocationContext ctx ) throws Exception
    {
        manager.getTransaction().begin();
        Object result = ctx.proceed();
        manager.getTransaction().commit();
        
        return result;
    }
}
