package com.mn.engcivil.domain.repositories;

import com.mn.engcivil.domain.Rule;
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
