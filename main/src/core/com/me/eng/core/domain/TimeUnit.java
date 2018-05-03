/* 
 *  Filename:    TimeUnit 
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Matheus
 */
public enum TimeUnit
{
    DAY( "dias" ) 
    {
        @Override
        public Date plus( Date date, long amount )
        {
            return Date.from( LocalDateTime.ofInstant( date.toInstant(), ZoneId.systemDefault() )
                    .plusDays( amount )
                    .atZone( ZoneId.systemDefault() )
                    .toInstant() );
        }
        
        @Override
        public long between( Date from, Date until )
        {
            return LocalDateTime.ofInstant( from.toInstant() .truncatedTo( ChronoUnit.DAYS ), ZoneId.systemDefault() )
                   .until( 
                   LocalDateTime.ofInstant( until.toInstant().truncatedTo( ChronoUnit.DAYS ), ZoneId.systemDefault() ), 
                   ChronoUnit.DAYS );
        }
    },
    
    HOURS( "horas" )
    {
        @Override
        public Date plus( Date date, long amount )
        {
            return Date.from( LocalDateTime.ofInstant( date.toInstant(), ZoneId.systemDefault() )
                    .plusHours( amount )
                    .atZone( ZoneId.systemDefault() )
                    .toInstant() );
        }

        @Override
        public long between( Date from, Date until )
        {
            return LocalDateTime.ofInstant( from.toInstant(), ZoneId.systemDefault() )
                   .until( 
                   LocalDateTime.ofInstant( until.toInstant(), ZoneId.systemDefault() ), 
                   ChronoUnit.HOURS );
        }
    };
    
    private String label;

    /**
     * TimeUnit
     * 
     * @param label String
     */
    private TimeUnit( String label )
    {
        this.label = label;
    }

    /**
     * getLabel
     * 
     * @return String
     */
    public String getLabel()
    {
        return label;
    }
    
    /**
     * Date
     * 
     * @param date Date
     * @param amount long
     * @return abstract
     * @ignored plus
     */
    public abstract Date plus( Date date, long amount );
    
    /**
     * long
     * 
     * @param form Date
     * @param until Date
     * @return abstract
     * @ignored between
     */
    public abstract long between( Date form, Date until );
    
    /**
     * fixDate
     * 
     * @param date Date
     * @return Date
     */
    public static Date fixDate( Date date )
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( date );
        calendar.set( Calendar.HOUR_OF_DAY, 0 );
        calendar.set( Calendar.MINUTE, 0 );
        calendar.set( Calendar.SECOND, 0 );
        calendar.set( Calendar.MILLISECOND, 0 );
        
        return calendar.getTime();
    }
}
