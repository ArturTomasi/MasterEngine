/* 
 *  Filename:    MD5Test 
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
package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Matheus
 */
public class MD5Test
{
    public static void main( String[] args )
    {
        try
        {
            System.out.println( DigestUtils.md5Hex( "admin" ) );
        }
        
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
}
