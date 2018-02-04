package com.me.eng.domain;

/**
 *
 * @author Matheus
 */
@Newtown
public class NewtownFormula
    implements 
        Formula
{
    @Override
    public Number evaluate( Number value )
    {
        return value.intValue() * 9.80665d;
    }
}
