/* 
 *  Filename:    Security 
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

import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Matheus
 */
public class Security
{
    private static final String ALGORITHM = "AES";
    private static final String KEY = "Y1FEKJ8nQhT472vNToIpoQ==";

    /**
     * encrypt
     * 
     * @param content String
     * @return String
     */
    public static String encrypt( String content ) 
    {
        try
        {
            Key key = new SecretKeySpec( Base64.getDecoder().decode( KEY ), ALGORITHM );

            Cipher c = Cipher.getInstance( ALGORITHM );
            c.init( Cipher.ENCRYPT_MODE, key );

            byte[] bytes = c.doFinal( content.getBytes() );

            return new String( Base64.getEncoder().encode( bytes ) );
        }
        
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
    }
    
    /**
     * decrypt
     * 
     * @param content String
     * @return String
     */
    public static String decrypt( String content )
    {
        try
        {
            Key key = new SecretKeySpec( Base64.getDecoder().decode( KEY ), ALGORITHM );

            Cipher c = Cipher.getInstance( ALGORITHM );

            c.init( Cipher.DECRYPT_MODE, key );

            byte bytes[] = Base64.getDecoder().decode( content );

            return new String( c.doFinal( bytes ) );
        }
        
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }
    }
}
