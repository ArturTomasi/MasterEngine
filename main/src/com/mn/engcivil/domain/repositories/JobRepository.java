package com.mn.engcivil.domain.repositories;

import com.mn.engcivil.domain.Client;
import com.mn.engcivil.domain.Job;
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
