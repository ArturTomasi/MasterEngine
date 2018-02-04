package com.me.eng.domain.repositories;

import com.me.eng.domain.Client;
import com.me.eng.domain.Job;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Matheus
 */
@Named
public interface JobRepository
    extends 
        EntityRepository<Job>
{
    public int countSamples( Job job ) throws Exception;
    public List<Job> getJobs( Client client ) throws Exception;
}
