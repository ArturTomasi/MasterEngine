package com.me.eng.domain;

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
public class CEI 
    implements 
        Serializable
{
    public static final String MASK = "99.999.999/9999-99";
    
    @Column(name = "cei")
    private String number = "";

    public CEI()
    {
    }

    public CEI( String number )
    {
        this.number = number;
    }
    
    public String getNumber()
    {
        return number;
    }

    public CEI setNumber( String number )
    {
        return new CEI( number );
    }
    
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

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode( this.number );
        return hash;
    }

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
        final CEI other = (CEI) obj;
        if ( !Objects.equals( this.number, other.number ) )
        {
            return false;
        }
        return true;
    }

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
