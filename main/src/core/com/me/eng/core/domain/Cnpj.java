/* 
 *  Filename:    Cnpj 
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

import com.google.common.base.Strings;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Matheus
 */
@Embeddable
public class Cnpj 
    implements 
        Serializable
{
    public static final String MASK = "99.999.999/9999-99";
    
    @Column(name = "cnpj")
    private String number = "";

    /**
     * Cnpj
     * 
     */
    public Cnpj()
    {
    }

    /**
     * Cnpj
     * 
     * @param number String
     */
    public Cnpj( String number )
    {
        this.number = number;
    }
    
    /**
     * getNumber
     * 
     * @return String
     */
    public String getNumber()
    {
        return number;
    }

    /**
     * setNumber
     * 
     * @param number String
     * @return Cnpj
     */
    public Cnpj setNumber( String number )
    {
        return new Cnpj( number );
    }
    
    /**
     * isValid
     * 
     * @return boolean
     */
    public boolean isValid()
    {
        if ( ! Strings.isNullOrEmpty( number ) && number.length() == 14 )
        {
            if ( ! number.matches( "(0{14}|1{14}|2{14}|3{14}|4{14}|5{14}|6{14}|7{14}|8{14}|9{14})" ) )
            {
                int dv1 = dv( number.substring( 0, 12 ) );
                int dv2 = dv( number.substring( 0, 13 ) );
                
                return Integer.parseInt( String.valueOf( number.charAt( 12 ) ) ) == dv1 &&
                       Integer.parseInt( String.valueOf( number.charAt( 13 ) ) ) == dv2;
            }
        }
        
        return false;
    }
    
    /**
     * dv
     * 
     * @param number String
     * @return int
     */
    private int dv( String number )
    {
        int mult = 2;
        
        int sum = 0;
        
        for ( int index = number.length() - 1; index >= 0; index-- )
        {
            sum += ( mult * Integer.parseInt( String.valueOf( number.charAt( index ) ) ) );
            
            if ( ++mult > 9 )
            {
                mult = 2;
            }
        }
        
        int dv = sum % 11;
        
        if ( dv < 2 )
        {
            dv = 0;
        }
        
        else
        {
            dv = 11 - dv;
        }
        
        return dv;
    }

    /**
     * hashCode
     * 
     * @return int
     */
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode( this.number );
        return hash;
    }

    /**
     * equals
     * 
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null )
        {
            return false;
        }
        if ( getClass() != obj.getClass() )
        {
            return false;
        }
        final Cnpj other = (Cnpj) obj;
        if ( !Objects.equals( this.number, other.number ) )
        {
            return false;
        }
        return true;
    }

    /**
     * toString
     * 
     * @return String
     */
    @Override
    public String toString()
    {
        try
        {
            MaskFormatter maskFormatter = new MaskFormatter( MASK.replaceAll( "9", "#" ) );
            maskFormatter.setValueContainsLiteralCharacters( false );
            return maskFormatter.valueToString( number );
        }
        
        catch ( Exception e )
        {
            throw new InternalError( e );
        }
    }
}
