package com.me.eng.domain;

import java.util.Objects;

/**
 *
 * @author Matheus
 */
public class EncodeTypeVisitor
    implements 
        Configuration.TypeVisitor<String>
{
    private Configuration configuration;

    public EncodeTypeVisitor( Configuration configuration )
    {
        this.configuration = configuration;
    }
    
    @Override
    public String visitString()
    {
        return defaultEncode();
    }

    @Override
    public String visitInteger()
    {
        return defaultEncode();
    }
    
    @Override
    public String visitFlag()
    {
        return defaultEncode();
    }
    
    @Override
    public String visitEncrypt()
    {
        return Security.encrypt( defaultEncode() );
    }
    
    private String defaultEncode()
    {
        return Objects.toString( configuration.getDecodedValue(), "" );
    }
}
