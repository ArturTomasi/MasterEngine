/* 
 *  Filename:    SampleReport 
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

import com.me.eng.services.FormulaApplicationServices;
import com.me.eng.domain.Sample;
import java.io.File;
import java.util.List;

/**
 *
 * @author Matheus
 */
public class SampleReport
{
    private Sample root;
    
    private List<Sample> samples;
    
    private boolean letterhead;

    public SampleReport( Sample root )
    {
        if ( root == null )
        {
            throw new IllegalArgumentException( "Sample cannot be null" );
        }
        
        this.root = root;
    }

    public void setLetterhead( boolean letterhead )
    {
        this.letterhead = letterhead;
    }

    public boolean isLetterhead()
    {
        return letterhead;
    }
    
    public void setItems( List<Sample> samples )
    {
        this.samples = samples;
    }
    
    public List<Sample> getItems()
    {
        return this.samples;
    }
    
    public Sample getRoot()
    {
        return root;
    }
    
    public void generate( File out ) throws Exception
    {
        ItextReportGenerator itextReportGenerator = new ItextReportGenerator();
        itextReportGenerator.setNewton( FormulaApplicationServices.getCurrent().getNewton() );
        itextReportGenerator.setRc( FormulaApplicationServices.getCurrent().getRc() );

        ReportGenerator reportGenerator = itextReportGenerator;

        reportGenerator.generateReport( this, out );
    }
}
