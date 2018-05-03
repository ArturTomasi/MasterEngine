/* 
 *  Filename:    SecurityTest 
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

import com.me.eng.core.domain.Security;

/**
 *
 * @author Matheus
 */
public class SecurityTest
{
    public static void main( String[] args ) throws Exception
    {
        String teste = "";
        
        String encrypt = Security.encrypt( teste );
        String decrypt = Security.decrypt( encrypt );
        
        System.out.println( "Encrypt: " + encrypt );
        System.out.println( "Decrypt: " + decrypt );
    }
}
