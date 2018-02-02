package com.mn.engcivil.domain.reports;

import com.mn.engcivil.domain.Formula;
import com.mn.engcivil.domain.NewtownFormula;
import com.mn.engcivil.domain.RCFormula;
import com.mn.engcivil.domain.Sample;
import java.text.NumberFormat;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author Matheus
 */
public class SampleDatasource
    implements 
        JRDataSource
{
    private Iterator<Sample> iterator;
    
    private NumberFormat id_nf = NumberFormat.getInstance();
    private NumberFormat rc_nf = NumberFormat.getInstance();
    
    private Sample sample;
    
    private Formula newton = new NewtownFormula();
    private Formula rc = new RCFormula();

    public SampleDatasource( List<Sample> samples )
    {
        this.iterator = samples.iterator();
        
        this.id_nf.setMinimumIntegerDigits( 4 );
        this.rc_nf.setMaximumFractionDigits( 2 );
    }
    
    @Override
    public boolean next() throws JRException
    {
        boolean next = iterator.hasNext();
        
        if ( next )
        {
            this.sample = iterator.next();
        }
        
        return next;
    }

    @Override
    public Object getFieldValue( JRField jrf ) throws JRException
    {
        if ( jrf.getName().equals( "cp" ) )
        {
            return id_nf.format( sample.getId() );
        }
        
        else if ( jrf.getName().equals( "fc" ) )
        {
            return newton.evaluate( sample.getResistence() ).intValue();
        }
        
        else if ( jrf.getName().equals( "rc" ) )
        {
            return rc_nf.format( rc.evaluate( sample.getResistence() ) );
        }
        
        else if ( jrf.getName().equals( "rupture" ) )
        {
            return sample.getRuptureType();
        }
        
        else if ( jrf.getName().equals( "days" ) )
        {
            if ( sample.getDateRupture() != null &&
                 sample.getDateExecuted() != null )
            {
                return ChronoUnit.DAYS.between( sample.getDateExecuted().toInstant(), 
                                                sample.getDateRupture().toInstant() ) + " dias";
            }

            return "";
        }
        
        else if ( jrf.getName().equals( "nf" ) )
        {
            return "";
        }
        
        return sample.getName();
    }
}
