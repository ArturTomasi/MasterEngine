/* 
 *  Filename:    SampleFilterBuilder 
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
package com.me.eng.samples.domain;

import com.me.eng.core.domain.Client;

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
