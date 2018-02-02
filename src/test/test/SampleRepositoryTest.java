package test;

import com.mn.engcivil.domain.Sample;
import com.mn.engcivil.domain.SampleFilter;
import com.mn.engcivil.domain.SampleFilterBuilder;
import com.mn.engcivil.domain.repositories.SampleRepository;
import java.util.List;
import javax.inject.Inject;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Matheus
 */
@RunWith(WeldJUnitRunner.class)
public class SampleRepositoryTest
    extends 
        TestCase
{
    @Inject
    private SampleRepository sampleRepository;
    
    @Test
    public void testOnlySampleRootFilter() throws Exception
    {
        SampleFilter filter = SampleFilterBuilder.newBuilder()
                                        .withOnlyRootSamples( true )
                                        .build();
        
        List<Sample> items = sampleRepository.getSamples( filter );
        
        assertFalse( items.stream()
                        .filter( item -> item.getParent() != null )
                        .findAny().isPresent() );
    }
}
