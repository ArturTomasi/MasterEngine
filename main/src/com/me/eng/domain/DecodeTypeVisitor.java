package com.me.eng.domain;

import com.google.common.base.Strings;
import com.me.eng.application.ConfigurationManager;

/**
 *
 * @author Matheus
 */
public class DecodeTypeVisitor
    implements 
        Configuration.TypeVisitor
{
    private Configuration configuration;

    public DecodeTypeVisitor( Configuration configuration )
    {
        this.configuration = configuration;
    }
    
    @Override
    public Object visitString()
    {
        return defaultDecode();
    }

    @Override
    public Object visitInteger()
    {
        String value = defaultDecode();
        
        if ( ! Strings.isNullOrEmpty( value ) && value.matches( "[0-9]*" ) )
        {
            return Integer.parseInt( value );
        }
        
        return null;
    }

    @Override
    public Object visitFlag()
    {
        return Boolean.valueOf( defaultDecode() );
    }

    @Override
    public Object visitEncrypt()
    {
        return Security.decrypt( defaultDecode() );
    }
    
    private String defaultDecode()
    {
        return ConfigurationManager.getInstance().getProperty( configuration.getProperty(), "" );
    }
}
