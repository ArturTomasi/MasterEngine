package com.mn.engcivil.domain;

/**
 *
 * @author Matheus
 */
@RC
public class RCFormula
    implements 
        Formula
{
    @Override
    public Number evaluate( Number value )
    {
        return ( new NewtownFormula().evaluate( value ).doubleValue() * 4 * 9.80665 ) / ( 3.14 * 100 );
    }
}
