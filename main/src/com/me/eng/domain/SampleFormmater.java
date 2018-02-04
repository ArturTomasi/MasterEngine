package com.me.eng.domain;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 *
 * @author Matheus
 */
public class SampleFormmater
{
    public static SampleFormmater newInstance()
    {
        return new SampleFormmater();
    }

    private String patternDate = "dd/MM/YYYY";

    public void setPatternDate( String patternDate )
    {
        this.patternDate = patternDate;
    }
    
    public String formatProofs( Sample sample )
    {
        StringBuilder sb = new StringBuilder();
        
        int index = 0;
        
        for ( Sample proof : sample.getProofs() )
        {
            if ( index % 4 == 0 && index > 0 )
            {
                sb.append( "\n" );
            }
            
            index++;
                
            sb.append( formatId( proof ) ).append(  " " );
        }
        
        return sb.toString();
    }
    
    public String formatId( Sample sample )
    {
        if ( sample != null && sample.getId() != null )
        {
            NumberFormat nf = NumberFormat.getIntegerInstance();
            nf.setMinimumIntegerDigits( 4 );
            nf.setGroupingUsed( false );

            return nf.format( sample.getId() );
        }
        
        return "";
    }
    
    public String formatResistence( Sample sample )
    {
        return formatFC( sample.getResistence() );
    }
    
    public String formatFC( Number v )
    {
        return formatRC( v );
    }
    
    public String formatRC( Number v )
    {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits( 1 );
        
        return nf.format( v );
    }
    
    public String formatDate( Date date )
    {
        return formatDate( date, patternDate );
    }
    
    public String formatDate( Date date, String pattern )
    {
        if ( date != null )
        {
            SimpleDateFormat df = new SimpleDateFormat( pattern );
            return df.format( date );
        }
        
        return "";
    }
    
    public String formatCapstones( Sample sample )
    {
        StringBuilder sb = new StringBuilder();
        
        for ( Iterator<Capstone> it = sample.getCapstones().iterator(); it.hasNext(); )
        {
            sb.append( it.next() ); 
            
            if ( it.hasNext() )
            {
                sb.append(  " / " );
            }
        }
        
        return sb.toString();
    }
    
    public String formatAge( Sample sample )
    {
        TimeUnit unit = TimeUnit.values()[ sample.getEstimatedUnitRupture() ];
            
        if ( sample.getDateRupture() != null && sample.getDateExecuted() != null )
        {
            return unit.between( sample.getDateExecuted(), sample.getDateRupture() ) + " " + unit.getLabel();
        }
        
        return "";
    }
}
