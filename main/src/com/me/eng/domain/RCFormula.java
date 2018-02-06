package com.me.eng.domain;

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
        return ( new NewtownFormula().evaluate( value ).doubleValue() * 4 ) / ( 3.14 * 1000 );
    }
}
