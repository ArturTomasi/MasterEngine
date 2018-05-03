/* 
 *  Filename:    EncodeTypeVisitor 
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

    /**
     * EncodeTypeVisitor
     * 
     * @param configuration Configuration
     */
    public EncodeTypeVisitor( Configuration configuration )
    {
        this.configuration = configuration;
    }
    
    /**
     * visitString
     * 
     * @return String
     */
    @Override
    public String visitString()
    {
        return defaultEncode();
    }

    /**
     * visitInteger
     * 
     * @return String
     */
    @Override
    public String visitInteger()
    {
        return defaultEncode();
    }
    
    /**
     * visitFlag
     * 
     * @return String
     */
    @Override
    public String visitFlag()
    {
        return defaultEncode();
    }
    
    /**
     * visitEncrypt
     * 
     * @return String
     */
    @Override
    public String visitEncrypt()
    {
        return Security.encrypt( defaultEncode() );
    }
    
    /**
     * defaultEncode
     * 
     * @return String
     */
    private String defaultEncode()
    {
        return Objects.toString( configuration.getDecodedValue(), "" );
    }
}
