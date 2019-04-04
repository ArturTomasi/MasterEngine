/*
 * Filename:    SectorRepository 
 *
 * Author:      Artur Tomasi
 * EMail:       tomasi.artur@gmail.com
 * Internet:    https://www.masterengine.com.br
 *
 * Copyright © 2019 by Over Line Ltda.
 * 95900-038, LAJEADO, RS
 * BRAZIL
 *
 * The copyright to the computer program(s) herein
 * is the property of Over Line Ltda., Brazil.
 * The program(s) may be used and/or copied only with
 * the written permission of Over Line Ltda.
 * or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which
 * the program(s) have been supplied.
 */
package com.me.eng.core.repositories;

import com.me.eng.core.domain.Sector;
import javax.inject.Named;

/**
 *
 * @author Artur Tomasi
 */
@Named
public interface SectorRepository 
    extends 
        EntityRepository<Sector>
{
    /**
     * findByName
     * 
     * @param name String
     * @return Sector
     * @throws Exception
     */
    public Sector findByName( String name ) throws Exception;
}
