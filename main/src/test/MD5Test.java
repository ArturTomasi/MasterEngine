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
