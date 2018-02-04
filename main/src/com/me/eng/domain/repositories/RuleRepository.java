package com.me.eng.domain.repositories;

import com.me.eng.domain.Rule;
import javax.inject.Named;

/**
 *
 * @author Matheus
 */
@Named
public interface RuleRepository
    extends 
        EntityRepository<Rule>
{
    public int countSamples( Rule rule ) throws Exception;
}
