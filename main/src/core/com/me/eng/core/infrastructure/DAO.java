/* 
 *  Filename:    DAO 
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
    /**
     * add
     * 
     * @param value T
     */
    public void add( T value );
    
    /**
     * update
     * 
     * @param value T
     */
    public void update( T value );
    
    /**
     * delete
     * 
     * @param value T
     */
    public void delete( T value );
    
    /**
     * findAll
     * 
     * @return List&lt;T&gt;
     */
    public List<T> findAll();
}
