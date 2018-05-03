/* 
 *  Filename:    DecodeTypeVisitor 
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
import com.me.eng.core.application.ConfigurationManager;

/**
 *
 * @author Matheus
 */
public class DecodeTypeVisitor
    implements 
        Configuration.TypeVisitor
{
    private Configuration configuration;

    /**
     * DecodeTypeVisitor
     * 
     * @param configuration Configuration
     */
    public DecodeTypeVisitor( Configuration configuration )
    {
        this.configuration = configuration;
    }
    
    /**
     * visitString
     * 
     * @return Object
     */
    @Override
    public Object visitString()
    {
        return defaultDecode();
    }

    /**
     * visitInteger
     * 
     * @return Object
     */
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

    /**
     * visitFlag
     * 
     * @return Object
     */
    @Override
    public Object visitFlag()
    {
        return Boolean.valueOf( defaultDecode() );
    }

    /**
     * visitEncrypt
     * 
     * @return Object
     */
    @Override
    public Object visitEncrypt()
    {
        return Security.decrypt( defaultDecode() );
    }
    
    /**
     * defaultDecode
     * 
     * @return String
     */
    private String defaultDecode()
    {
        return ConfigurationManager.getInstance().getProperty( configuration.getProperty(), "" );
    }
}
