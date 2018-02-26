/* 
 *  Filename:    SampleDatasourceFactory 
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
package com.me.eng.domain.reports;

import com.me.eng.domain.Sample;
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
