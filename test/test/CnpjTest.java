package test;

import com.mn.engcivil.domain.Cnpj;

/**
 *
 * @author Matheus
 */
public class CnpjTest
{
    public static void main( String[] args )
    {
        System.out.println( new Cnpj( "14572457000585" ).isValid() );
    }
}
