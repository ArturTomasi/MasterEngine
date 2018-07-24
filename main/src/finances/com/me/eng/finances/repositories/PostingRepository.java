/*
 *  Filename:    PostingRepository
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
package com.me.eng.finances.repositories;

import com.me.eng.core.data.ChartData;
import com.me.eng.core.domain.User;
import com.me.eng.core.repositories.EntityRepository;
import com.me.eng.finances.domain.Posting;
import com.me.eng.finances.domain.PostingFilter;
import java.util.List;

/**
 *
 * @author Artur Tomasi
 */
public interface PostingRepository 
    extends 
        EntityRepository<Posting> 
{

    /**
     * Posting
     * 
     * @param current Posting
     * @return abstract
     * @throws Exception
     * @ignored getLastPosting
     */
    public abstract Posting getLastPosting( Posting current ) throws Exception;
    
    /**
     * void
     * 
     * @param posting Posting
     * @return abstract
     * @throws Exception
     * @ignored updateNextPortion
     */
    public abstract void updateNextPortion( Posting posting ) throws Exception;
    
    /**
     * void
     * 
     * @param posting Posting
     * @return abstract
     * @throws Exception
     * @ignored deleteNextPortions
     */
    public abstract void deleteNextPortions( Posting posting ) throws Exception;
    
    /**
     * List&lt;Posting&gt;
     * 
     * @param filter PostingFilter
     * @return abstract
     * @throws Exception
     * @ignored find
     */
    public abstract List<Posting> find( PostingFilter filter ) throws Exception;
    
    
    /**
     * Map&lt;PostingType,
     * 
     * @return abstract
     * @ignored Double&gt;
     * @ignored sum
     */
    public abstract List<ChartData> sum();
}
