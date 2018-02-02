
package test;

import com.mn.engcivil.domain.Security;

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
