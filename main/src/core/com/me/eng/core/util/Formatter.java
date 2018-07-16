/*
 *  Filename:    Formatter
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
package com.me.eng.core.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Artur Tomasi
 */
public class Formatter 
{
    private static Formatter instance;

    /**
     * getInstance
     * 
     * @return Formatter
     */
    public static Formatter getInstance() 
    {
        if ( instance == null )
        {
            instance = new Formatter();
        }
        
        return instance;
    }
    
    private DateFormat df       = new SimpleDateFormat( "dd/MM/YYYY" );
    private Locale locale       = new Locale( "pt", "BR" );
    private NumberFormat nf     = NumberFormat.getInstance( locale );
    private NumberFormat cf     = NumberFormat.getCurrencyInstance( locale );

    /**
     * Formatter
     * 
     */
    private Formatter()
    {
        nf.setMinimumFractionDigits( 2 );
        nf.setMaximumFractionDigits( 2 );
        cf.setMinimumFractionDigits( 2 );
        cf.setMaximumFractionDigits( 2 );
    }
    
    /**
     * getLocale
     * 
     * @return Locale
     */
    public Locale getLocale() 
    {
        return locale;
    }

    /**
     * setLocale
     * 
     * @param locale Locale
     */
    public void setLocale( Locale locale ) 
    {
        this.locale = locale;
    }
    
    /**
     * formatDate
     * 
     * @param date Date
     * @return String
     */
    public String formatDate( Date date )
    {
        if ( date == null )
        {
            return "n/d";
        }
        
        return df.format( date );
    }
    
    /**
     * formatMonth
     * 
     * @param date Date
     * @return String
     */
    public String formatMonth( Date date )
    {
        if ( date == null )
        {
            return "n/d";
        }
        
        Calendar cal = Calendar.getInstance( locale );
        cal.setTime( date );
        
        return cal.getDisplayName( Calendar.MONTH, Calendar.LONG_FORMAT, locale );
    }
    
    /**
     * formatMonth
     * 
     * @param month Integer
     * @return String
     */
    public String formatMonth( Integer month )
    {
        Calendar cal = Calendar.getInstance( locale );
        cal.set( Calendar.MONTH, month - 1 );
        
        return cal.getDisplayName( Calendar.MONTH, Calendar.LONG_FORMAT, locale );
    }
    
    /**
     * formatCurrency
     * 
     * @param currency Double
     * @return String
     */
    public String formatCurrency( Double currency )
    {
        if ( currency == null )
        {
            return "n/d";
        }
        
        return cf.format( currency );
    }
}
