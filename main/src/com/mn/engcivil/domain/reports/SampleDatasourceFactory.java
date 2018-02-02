package com.mn.engcivil.domain.reports;

import com.mn.engcivil.domain.Sample;
import java.util.Collections;
import net.sf.jasperreports.engine.JRDataSource;

/**
 *
 * @author Matheus
 */
public class SampleDatasourceFactory
{
    public static JRDataSource newDatasource()
    {
        Sample sample = new Sample();
        sample.setName( "Teste" );
        
        return new SampleDatasource( Collections.singletonList( sample ) );
    }
}
