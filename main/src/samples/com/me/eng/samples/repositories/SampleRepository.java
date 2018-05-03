/* 
 *  Filename:    SampleRepository 
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
package com.me.eng.samples.repositories;

import com.me.eng.core.repositories.EntityRepository;
import com.me.eng.core.domain.Client;
import com.me.eng.samples.domain.Job;
import com.me.eng.samples.domain.Sample;
import com.me.eng.samples.domain.SampleFilter;
import com.me.eng.samples.domain.SampleMail;
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
