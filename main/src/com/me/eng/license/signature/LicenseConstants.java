/*
 *  Filename:    LicenseConstants
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
package com.me.eng.license.signature;

import com.me.eng.application.ResourceLocator;
import java.sql.Date;

/**
 *
 * @author Artur Tomasi
 */
public enum LicenseConstants 
{
    LICENSE_BEGIN(  "#\n"                                                      +
                    "#  Filename:    license.me\n"                             +
                    "#\n"                                                      +
                    "#  Author:      Artur Tomasi\n"                           +
                    "#  EMail:       tomasi.artur@gmail.com\n"                 +
                    "#  Internet:    www.masterengine.com.br\n"                +
                    "#\n"                                                      +
                    "#  Copyright © 2018 by Over Line Ltda.\n"                 +
                    "#  95900-038, LAJEADO, RS\n"                              +
                    "#  BRAZIL\n"                                              +
                    "#\n"                                                      +
                    "#  The copyright to the computer program(s) herein\n"     +
                    "#  is the property of Over Line Ltda., Brazil.\n"         +
                    "#  The program(s) may be used and/or copied only with\n"  +
                    "#  the written permission of Over Line Ltda.\n"           +
                    "#  or in accordance with the terms and conditions\n"      +
                    "#  stipulated in the agreement/contract under which\n"    +
                    "#  the program(s) have been supplied.\n"                  +
                    "#" ),
    
    LICENSE_END(    "#################################################################" ),

    
    SIGNATURE_LINE_LENGTH( String.valueOf( LICENSE_END.stringValue().length() ) ),
    SIGNATURE_TOKEN( "=" ),
    
    PUBLIC_KEY_PATH(  ResourceLocator.getResourceAsURL( "/signatures/public_key.pk" ).getPath() ),
    PRIVATE_KEY_PATH( ResourceLocator.getResourceAsURL( "/signatures/private_key.pk" ).getPath() ),
    LICENSE_PATH(     ResourceLocator.getSharedFile( "license.me" ) );
    
    private String value;

    /**
     * LicenseVariables
     * 
     * @param value String
     */
    private LicenseConstants( String value ) 
    {
        this.value = value;
    }

    /**
     * getValue
     * 
     * @return String
     */
    public String stringValue() 
    {
        return value;
    }
    
    /**
     * intValue
     * 
     * @return int
     */
    public int intValue() 
    {
        return Integer.valueOf( value );
    }
    
    /**
     * dateValue
     * 
     * @return int
     */
    public Date dateValue() 
    {
        return new Date( Long.valueOf( value ) );
    }
}
