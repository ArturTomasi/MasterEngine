/* 
 *  Filename:    SampleFormmater 
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
package com.me.eng.samples.domain;

import com.me.eng.core.domain.TimeUnit;
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
    /**
     * newInstance
     * 
     * @return SampleFormmater
     */
    public static SampleFormmater newInstance()
    {
        return new SampleFormmater();
    }

    private String patternDate = "dd/MM/YYYY";

    /**
     * setPatternDate
     * 
     * @param patternDate String
     */
    public void setPatternDate( String patternDate )
    {
        this.patternDate = patternDate;
    }
    
    /**
     * formatProofs
     * 
     * @param sample Sample
     * @return String
     */
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
    
    /**
     * formatId
     * 
     * @param sample Sample
     * @return String
     */
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
    
    /**
     * formatResistence
     * 
     * @param sample Sample
     * @return String
     */
    public String formatResistence( Sample sample )
    {
        return formatFC( sample.getResistence() );
    }
    
    /**
     * formatFC
     * 
     * @param v Number
     * @return String
     */
    public String formatFC( Number v )
    {
        return formatRC( v );
    }
    
    /**
     * formatRC
     * 
     * @param v Number
     * @return String
     */
    public String formatRC( Number v )
    {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits( 1 );
        
        return nf.format( v );
    }
    
    /**
     * formatFCK
     * 
     * @param s Sample
     * @return String
     */
    public String formatFCK( Sample s )
    {
        try
        {
            Double.valueOf( s.getTrace() );
            
            return "FCK " + s.getTrace();
        } 
        
        catch ( Exception e ){ /*silent*/ }
        
        return s.getTrace();
    }
    
    /**
     * formatName
     * 
     * @param sample Sample
     * @return String
     */
    public String formatName( Sample sample )
    {
        String fck = formatFCK( sample );
        
        if ( fck != null )
        {
            fck = fck.trim();
            
            if ( ! sample.getName().toLowerCase().startsWith( fck.toLowerCase() ) )
            {
                return fck + " " + sample.getName();
            }
        }
        
        return sample.getName();
    }
    
    /**
     * formatRupture
     * 
     * @param sample Sample
     * @return String
     */
    public String formatRupture( Sample sample )
    {
        if ( sample.getEstimatedUnitRupture() == TimeUnit.HOURS.ordinal() )
        {
            return formatDate( sample.getDateRupture(), "dd/MM/yyyy HH:mm" );
        }
        
        return formatDate( sample.getDateRupture(), "dd/MM/yyyy" );
    }
    
    /**
     * formatDate
     * 
     * @param date Date
     * @return String
     */
    public String formatDate( Date date )
    {
        return formatDate( date, patternDate );
    }
    
    /**
     * formatDate
     * 
     * @param date Date
     * @param pattern String
     * @return String
     */
    public String formatDate( Date date, String pattern )
    {
        if ( date != null )
        {
            SimpleDateFormat df = new SimpleDateFormat( pattern );
            return df.format( date );
        }
        
        return "";
    }
    
    /**
     * formatCapstones
     * 
     * @param sample Sample
     * @return String
     */
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
    
    /**
     * formatAge
     * 
     * @param sample Sample
     * @return String
     */
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
