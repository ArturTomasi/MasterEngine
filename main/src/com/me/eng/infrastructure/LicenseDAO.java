/*
 *  Filename:    LicenseDAO
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

import com.me.eng.domain.License;
import com.me.eng.domain.User;
import com.me.eng.domain.repositories.LicenseRepository;
import javax.enterprise.inject.Default;
import javax.persistence.Query;

/**
 *
 * @author Artur Tomasi
 */
@Default
public class LicenseDAO 
    extends 
        EntityDAO<License>
            implements 
                LicenseRepository
{
    /**
     * LicenseDAO
     * 
     */
    public LicenseDAO()
    {
        super( License.class );
    }

    /**
     * purge
     * 
     * @param license License
     * @throws Exception
     */
    @Override
    @Transactional
    public void purge( License license ) throws Exception 
    {
        Query query = manager.createQuery( "delete from License where session = :session and module = :module" );
        query.setParameter( "module", license.getModule() );
        query.setParameter( "session", license.getSession() );
        
        query.executeUpdate();
    }

    /**
     * cleanup
     * 
     * @param session String
     * @param user User
     * @throws Exception
     */
    @Override
    @Transactional
    public void cleanup( String session, User user ) throws Exception 
    {
        Query query = manager.createQuery( "delete from License where session <> :session and user = :user" );
        
        query.setParameter( "user",    user );
        query.setParameter( "session", session );
        
        query.executeUpdate();
    }
    
    
    /**
     * cleanup
     * 
     * @throws Exception
     */
    @Override
    @Transactional
    public void cleanup() throws Exception 
    {
        Query query = manager.createQuery( "delete from License" );
        
        query.executeUpdate();
    }
}
