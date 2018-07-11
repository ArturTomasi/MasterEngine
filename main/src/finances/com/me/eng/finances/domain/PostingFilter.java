/*
 *  Filename:    PostingFilter
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
 *  is the property of Over Line Ltda.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda,
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.finances.domain;

import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.domain.User;
import com.me.eng.core.services.ApplicationServices;
import com.me.eng.finances.repositories.PostingRepository;
import java.util.List;

/**
 *
 * @author Artur Tomasi
 */
public enum PostingFilter 
{
    ALL()
    {
        @Override
        public List<Posting> getElements() throws Exception
        {
            return getRepository().findAll( getUser() );
        }
        
    },
    
    PENDENCY()
    {
        @Override
        public List<Posting> getElements() throws Exception
        {
            return getRepository().findPendency( getUser() );
        }
        
    };
    
    public abstract List<Posting> getElements() throws Exception;
    
    /**
     * getRepository
     * 
     * @return PostingRepository
     */
    private static PostingRepository getRepository()
    {
        return ApplicationServices.getCurrent().getPostingRepository();
    }
    
    /**
     * getUser
     * 
     * @return User
     */
    private static User getUser()
    {
        return ApplicationContext.getInstance().getActiveUser();
    }
}
