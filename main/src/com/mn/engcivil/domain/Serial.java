package com.mn.engcivil.domain;

import java.text.NumberFormat;
import java.util.Calendar;

/**
 *
 * @author Matheus
 */
public class Serial
{
    private String majorNumber;
    private String minorNumber;
    
    public Serial( Sample sample )
    {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumIntegerDigits( 4 );
        nf.setGroupingUsed( false );
        
        this.majorNumber = nf.format( sample.getId() );
        
        Calendar c = new Calendar.Builder()
                .setInstant( sample.getDateCreated() )
                .build();
        
        this.minorNumber = String.valueOf( c.get( Calendar.YEAR ) * 100 +
                                           c.get( Calendar.MONTH ) + 1 );
    }

    public String getMajorNumber()
    {
        return majorNumber;
    }

    public String getMinorNumber()
    {
        return minorNumber;
    }

    @Override
    public String toString()
    {
        return majorNumber + "-" + minorNumber;
    }
}
