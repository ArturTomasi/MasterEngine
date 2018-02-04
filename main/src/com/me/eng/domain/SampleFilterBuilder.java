package com.me.eng.domain;

/**
 *
 * @author Matheus
 */
public class SampleFilterBuilder
{
    public static SampleFilterBuilder newBuilder()
    {
        return new SampleFilterBuilder();
    }

    private SampleFilter filter = new SampleFilter();
    
    public SampleFilterBuilder withClient( Client client )
    {
        filter.setClient( client );
        return this;
    }
    
    @Deprecated
    public SampleFilterBuilder withOnlyRootSamples( boolean value )
    {
        filter.setOnlyRootSamples( value );
        return this;
    }
    
    public SampleFilter build()
    {
        return filter;
    }
}
