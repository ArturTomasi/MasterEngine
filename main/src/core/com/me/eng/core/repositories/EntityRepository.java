/* 
 *  Filename:    EntityRepository 
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
package com.me.eng.core.repositories;

import java.util.List;

/**
 *
 * @author Matheus
 * @param <T>
 */
public interface EntityRepository<T>
{
    /**
     * add
     * 
     * @param u T
     * @throws Exception
     */
    public void add( T u ) throws Exception;
    
    /**
     * update
     * 
     * @param u T
     * @throws Exception
     */
    public void update( T u ) throws Exception;
    
    /**
     * delete
     * 
     * @param u T
     * @throws Exception
     */
    public void delete( T u ) throws Exception;
    
    /**
     * findAll
     * 
     * @return List&lt;T&gt;
     * @throws Exception
     */
    public List<T> findAll() throws Exception;
}
