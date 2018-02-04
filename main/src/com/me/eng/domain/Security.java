package com.me.eng.domain;

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
