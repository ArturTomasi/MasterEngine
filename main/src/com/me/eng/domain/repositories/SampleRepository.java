
package com.me.eng.domain.repositories;

import com.me.eng.domain.Client;
import com.me.eng.domain.Job;
import com.me.eng.domain.Sample;
import com.me.eng.domain.SampleFilter;
import com.me.eng.domain.SampleMail;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Matheus
 */
@Named
public interface SampleRepository
    extends 
        EntityRepository<Sample>
{
    public List<Sample> getSamples( Job job ) throws Exception;
    public List<Sample> getSamples( Client client ) throws Exception;
    public List<Sample> getSamples( SampleFilter filter ) throws Exception;
    public List<Sample> getSamplesToNofitication() throws Exception;
    public Sample getLastSample( Client client ) throws Exception;
    
    public List<String> getDistinctNames() throws Exception;
    
    public void addSampleMail( SampleMail sampleMail ) throws Exception;
    public void updateSampleMail( SampleMail sampleMail ) throws Exception;
    public SampleMail getLastSampleMail( Sample sample ) throws Exception;
    public boolean hasSuccessSampleMail( Sample sample ) throws Exception;
}
