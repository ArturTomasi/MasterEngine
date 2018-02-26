/* 
 *  Filename:    Serial 
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
package com.me.eng.domain;

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
