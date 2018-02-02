package com.mn.engcivil.domain.reports;

import com.mn.engcivil.application.FormulaApplicationServices;
import com.mn.engcivil.domain.Sample;
import com.mn.engcivil.domain.SampleFilter;
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
