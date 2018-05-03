/* 
 *  Filename:    LicenseValidator 
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
package com.me.eng.core.license.signature;

import com.me.eng.core.license.exceptions.LicenseException;
import com.me.eng.core.util.Base64;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Artur Tomasi
 */
public class LicenseValidator 
{
    private PublicKey publicKey;
    private Signature signature;
    private StringBuffer licenseText = new StringBuffer();
    private byte[] licenseSignature;
    private Map<String, String> licenseOptions = new HashMap();

    /**
     * Get the value of license options (signed key-value pairs, if presents)
     *
     * @return key-value pairs of strings
     */
    public Map<String, String> getLicenseOptions() 
    {
        return licenseOptions;
    }

    /**
     * Get the value of signed license contents
     *
     * @return the value of license contents
     */
    public String getLicenseText()
    {
        return licenseText.toString();
    }

    /**
     * initializeSignatureVerify
     * 
     * @throws LicenseException
     */
    private void initializeSignatureVerify() throws LicenseException
    {
        try 
        {
            signature = Signature.getInstance("SHA1withDSA");
            
            signature.initVerify( publicKey );
        }
        
        catch ( Exception ex )
        {
            throw new LicenseException( "Error in initializing signature for verification ( " + ex.getMessage() + " )" );
        }
    }

    /**
     * processSourceFile
     * 
     * @param sourceFile String
     * @throws LicenseException
     */
    private void processSourceFile( String sourceFile ) throws LicenseException 
    {
        try
        {
            List<String> lines = Files.readAllLines( Paths.get( sourceFile ) );
            
            boolean isLicenseEnd = false;
            
            for ( String line : lines )
            {
                //do update
                if (  ! line.startsWith( "#" ) && ! line.equalsIgnoreCase( LicenseConstants.LICENSE_END.stringValue() ) && ! isLicenseEnd )
                {
                    signature.update( line.getBytes(), 0, line.getBytes().length );
                }
                
                else if ( line.equalsIgnoreCase( LicenseConstants.LICENSE_END.stringValue() ) && ! isLicenseEnd  )
                {
                    isLicenseEnd =  ! isLicenseEnd;
                }

                if ( ! line.startsWith( "#" ) )
                {
                    String [] prop = line.split( LicenseConstants.SIGNATURE_TOKEN.stringValue() );

                    if ( prop != null && prop.length == 2 )
                    {
                        licenseOptions.put( prop[0], prop[1] );
                    }

                    else
                    {
                        licenseSignature = Base64.decode( line );
                    }
                }
            }
        } 
        
        catch ( Exception ex )
        {
            throw new LicenseException( "Error in processing source file ( " + ex.getMessage() + " )" );
        } 
    }

    /**
     * readPublicKey
     * 
     * @param publicKeyFile String
     * @return boolean
     * @throws LicenseException
     */
    private boolean readPublicKey( String publicKeyFile ) throws LicenseException 
    {
        FileReader fileReader = null;
    
        BufferedReader bufferedReader;

        try 
        {
            if ( ! new File( publicKeyFile ).exists() )
            {
                return false;
            }

            String publicKeyString = "";

            fileReader = new FileReader( publicKeyFile );
            
            bufferedReader = new BufferedReader(fileReader);

            while ( bufferedReader.ready() )
            {
                publicKeyString += bufferedReader.readLine();
            }

            publicKey = KeyFactory.getInstance("DSA").generatePublic( new X509EncodedKeySpec( Base64.decode(publicKeyString ) ) );

            return true;
        }
        
        catch ( Exception ex )
        {
            throw new LicenseException( "Error in reading public key from file " + publicKeyFile + " ( " + ex.getMessage() + " )" );
        }
        
        finally 
        {
            try 
            {
                fileReader.close();
            }
            
            catch ( Exception ex ) {}
        }
    }

    /**
     * verifySignature
     * 
     * @return boolean
     * @throws LicenseException
     */
    private boolean verifySignature() throws LicenseException 
    {
        try 
        {
            return signature.verify( licenseSignature );
        }
        
        catch ( Exception ex )
        {
            throw new LicenseException( "Error in verification ( " + ex.getMessage() + " )" );
        }
    }

    /** Verifies a license file based on public DSA key
     *
     * @param publicKeyFile Public key file for checking
     * @param signatureFile Signature file to check
     * @return true when the signature file matches, otherwise false
     * @throws LicenseException Raised on IO or Crypthographic errors
     */
    public boolean verifyLicense( String publicKeyFile, String signatureFile ) throws LicenseException 
    {
        try 
        {
            readPublicKey( publicKeyFile );
        
            initializeSignatureVerify();
            
            processSourceFile(signatureFile);

            return verifySignature();
        } 
        
        catch ( Exception ex )
        {
            throw new LicenseException("Error in signature verification ( " + ex.getMessage() + " )");
        }
    }
}
