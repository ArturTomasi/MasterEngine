/* 
 *  Filename:    TransactionalManager 
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

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

/**
 *
 * @author Matheus
 */
@Interceptor @Transactional
public class TransactionalManager
{
    @Inject private EntityManager manager;
    
    @AroundInvoke
    public Object intercept( InvocationContext ctx ) throws Exception
    {
        manager.getTransaction().begin();
        
        Object result = ctx.proceed();
        
        manager.getTransaction().commit();
        
        return result;
    }
}
