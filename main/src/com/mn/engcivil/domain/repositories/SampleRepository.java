
package com.mn.engcivil.domain.repositories;

import com.mn.engcivil.domain.Client;
import com.mn.engcivil.domain.Job;
import com.mn.engcivil.domain.Sample;
import com.mn.engcivil.domain.SampleFilter;
import com.mn.engcivil.domain.SampleMail;
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
