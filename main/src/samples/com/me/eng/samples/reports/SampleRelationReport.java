/*
 *  Filename:    ListSamplesReport
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
 *  is the property of Over Line Ltda.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda,
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.samples.reports;

import com.itextpdf.text.Document;
import com.me.eng.core.reports.AbstractReport;
import com.me.eng.samples.domain.Sample;
import com.me.eng.samples.domain.SampleFormmater;
import java.util.List;

/**
 *
 * @author Artur Tomasi
 */
public class SampleRelationReport 
    extends 
        AbstractReport
{
    private List<Sample> samples;

    /**
     * SampleRelationReport
     * 
     */
    public SampleRelationReport(){}

    
    /**
     * setSamples
     * 
     * @param samples List&lt;Sample&gt;
     */
    public void setSamples( List<Sample> samples )
    {
        this.samples = samples;
    }

    /**
     * generateDocument
     * 
     * @param document Document
     * @throws Exception
     */
    @Override
    protected void generateDocument( Document document ) throws Exception 
    {
        setTitle( "Relação de Amostras" );
        
        Table table = new Table( 10, 10, 10, 40, 10, 10, 10 );
        table.setHeader( "C.P. (Nº)", "Tonelada", "Tipo", "Corpo de Prova", "Idade", "Nota Fiscal", "Ruptura" );
 
        SampleFormmater sf = new SampleFormmater();
        
        samples.forEach( (sample) -> 
        {
            table.addRow( sf.formatId( sample ), "", "", 
                          sf.formatName( sample ),
                          sf.formatAge( sample ),
                          sample.getNf(),
                          sf.formatRupture( sample ) );
        } );
        
        document.add( table );
    }
    
}
