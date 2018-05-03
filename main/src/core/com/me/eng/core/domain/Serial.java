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
package com.me.eng.core.domain;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Matheus
 */
public class Serial
{
    private String majorNumber;
    private String minorNumber;
    
    /**
     * Serial
     * 
     * @param id
     * @param cal
     */
    public Serial( Integer id, Date cal )
    {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMinimumIntegerDigits( 4 );
        nf.setGroupingUsed( false );
        
        this.majorNumber = nf.format( id );
        
        Calendar c = new Calendar.Builder()
                .setInstant( cal )
                .build();
        
        this.minorNumber = String.valueOf( c.get( Calendar.YEAR ) * 100 +
                                           c.get( Calendar.MONTH ) + 1 );
    }

    /**
     * getMajorNumber
     * 
     * @return String
     */
    public String getMajorNumber()
    {
        return majorNumber;
    }

    /**
     * getMinorNumber
     * 
     * @return String
     */
    public String getMinorNumber()
    {
        return minorNumber;
    }

    /**
     * toString
     * 
     * @return String
     */
    @Override
    public String toString()
    {
        return majorNumber + "-" + minorNumber;
    }
}
