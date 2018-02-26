/* 
 *  Filename:    LicenseCreator 
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
 *  the written permission of Overline Ltda.
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.license.signature;

import com.me.eng.license.exceptions.LicenseException;
import com.me.eng.domain.util.Base64;
import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * 
 * @author Artur Tomasi
 */
public class LicenseCreator 
{
    private static final String EOL = System.getProperty( "line.separator" );
    
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Signature signature;
    private String licenseText;
    
    /**
     * generateKeys
     * 
     * @throws LicenseException
     */
    private void generateKeys() throws LicenseException 
    {
        try 
        {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
        
            SecureRandom random     = SecureRandom.getInstance("SHA1PRNG", "SUN");

            keyGen.initialize( 1024, random );
            
            KeyPair pair = keyGen.generateKeyPair();
            
            privateKey = pair.getPrivate();
            
            publicKey = pair.getPublic();
        } 
        
        catch ( Exception ex )
        {
            throw new LicenseException( "Error in key generation ( " + ex.getMessage() + " )" );
        }
    }

    /**
     * initializeSignatureSign
     * 
     * @throws LicenseException
     */
    private void initializeSignatureSign() throws LicenseException 
    {
        try 
        {
            signature = Signature.getInstance( "SHA1withDSA", "SUN" );
            
            signature.initSign( privateKey );
        } 
        
        catch ( Exception ex ) 
        {
            throw new LicenseException( "Error in initializing signature for signing ( " + ex.getMessage() + " )" );
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
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        
        try 
        {
            fileReader = new FileReader(sourceFile);
        
            bufferedReader = new BufferedReader(fileReader);
            
            boolean isLicense = true;
            
            String line;

            licenseText = new String();

            while (bufferedReader.ready() && isLicense) 
            {
                line = bufferedReader.readLine();
                
                if (!line.equals(LicenseConstants.LICENSE_BEGIN.stringValue() ) ) 
                {
                    licenseText += line + EOL;
                    signature.update(line.getBytes(), 0, line.getBytes().length);
                } 
                
                else 
                {
                    isLicense = false;
                }
            }

        } 
        
        catch ( Exception ex )
        {
            throw new LicenseException("Error in processing source file ( " + ex.getMessage() + " )");
        }
        
        finally 
        {
            try 
            {
                bufferedReader.close();
                fileReader.close();
            }
            
            catch ( Exception ex ){}
        }
    }

    /**
     * generateSignature
     * 
     * @return char[]
     * @throws LicenseException
     */
    private char[] generateSignature() throws LicenseException 
    {
        try 
        {
            return Base64.encode( signature.sign() );
        }
        
        catch ( Exception ex )
        {
            throw new LicenseException("Error in signature creation ( " + ex.getMessage() + " )");
        }
    }

    /**
     * writeSignature
     * 
     * @param signatureFile String
     * @throws LicenseException
     */
    private void writeSignature( String signatureFile ) throws LicenseException 
    {
        FileWriter fileWriter = null;

        try 
        {
            fileWriter = new FileWriter( signatureFile );
        
            char[] signatureString = generateSignature();

            fileWriter.write( LicenseConstants.LICENSE_BEGIN.stringValue() );
            fileWriter.write( EOL );
            fileWriter.write( licenseText );
            fileWriter.write( LicenseConstants.LICENSE_END.stringValue() );
            fileWriter.write( EOL );
            fileWriter.write( signatureString );
            fileWriter.write( EOL + LicenseConstants.LICENSE_END.stringValue() );
        } 
        
        catch ( Exception ex )
        {
            throw new LicenseException("Error in writing signature to file " + signatureFile + " ( " + ex.getMessage() + " )");
        }
        
        finally 
        {
            try 
            {
                fileWriter.close();
            }
            
            catch ( Exception ex ){}
        }
    }

    /**
     * writePrivateKey
     * 
     * @param privateKeyFile String
     * @throws LicenseException
     */
    private void writePrivateKey( String privateKeyFile ) throws LicenseException 
    {
        FileWriter fileWriter = null;

        try 
        {
            byte[] encodedPrivateKey = privateKey.getEncoded();
        
            fileWriter = new FileWriter(privateKeyFile);
            
            char[] privateKeyString = Base64.encode(encodedPrivateKey);

            for ( int i = 0; i < privateKeyString.length; i = i + LicenseConstants.SIGNATURE_LINE_LENGTH.intValue() )
            {
                fileWriter.write( privateKeyString, i, Math.min( privateKeyString.length - i, LicenseConstants.SIGNATURE_LINE_LENGTH.intValue() ) );
                
                if (privateKeyString.length - i > LicenseConstants.SIGNATURE_LINE_LENGTH.intValue() )
                {
                    fileWriter.write(EOL);
                }
            }
        }
        
        catch ( Exception ex )
        {
            throw new LicenseException("Error in writing private key to file " + privateKeyFile + " ( " + ex.getMessage() + " )");
        }
        
        finally 
        {
            try 
            {
                fileWriter.close();
            } 
            
            catch ( Exception ex ){}
        }
    }

    /**
     * writePublicKey
     * 
     * @param publicKeyFile String
     * @throws LicenseException
     */
    private void writePublicKey( String publicKeyFile ) throws LicenseException 
    {
        FileWriter fileWriter = null;

        try 
        {
            byte[] encodedPublicKey = publicKey.getEncoded();
        
            fileWriter = new FileWriter(publicKeyFile);
            
            char[] publicKeyString = Base64.encode(encodedPublicKey);

            for ( int i = 0; i < publicKeyString.length; i = i + LicenseConstants.SIGNATURE_LINE_LENGTH.intValue() ) 
            {
                fileWriter.write(publicKeyString, i, Math.min( publicKeyString.length - i, LicenseConstants.SIGNATURE_LINE_LENGTH.intValue() ) );
            
                if ( publicKeyString.length - i > LicenseConstants.SIGNATURE_LINE_LENGTH.intValue() )
                {
                    fileWriter.write( EOL );
                }
            }
        } 
        
        catch ( Exception ex )
        {
            throw new LicenseException("Error in writing public key to file " + publicKeyFile + " ( " + ex.getMessage() + " )");
        }
        
        finally
        {
            try 
            {
                fileWriter.close();
            }
            
            catch ( Exception ex ){}
        }
    }

    /**
     * readPrivateKey
     * 
     * @param privateKeyFile String
     * @return boolean
     * @throws LicenseException
     */
    private boolean readPrivateKey( String privateKeyFile ) throws LicenseException 
    {
        FileReader fileReader = null;
        
        BufferedReader bufferedReader;

        try 
        {
            if ( ! new File(privateKeyFile).exists() )
            {
                return false;
            }

            String privateKeyString = "";

            fileReader = new FileReader( privateKeyFile );
            bufferedReader = new BufferedReader( fileReader );

            while ( bufferedReader.ready() ) 
            {
                privateKeyString += bufferedReader.readLine();
            }

            privateKey = KeyFactory.getInstance( "DSA", "SUN" ).generatePrivate( new PKCS8EncodedKeySpec( Base64.decode( privateKeyString ) ) );

            return true;
        } 
        
        catch ( Exception ex )
        {
            throw new LicenseException( "Error in reading private key from file " + privateKeyFile + " ( " + ex.getMessage() + " )" );
        } 
        
        finally 
        {
            try 
            {
                fileReader.close();
            }
            
            catch ( Exception ex ){}
        }
    }


    /** Signs an input file by the provided DSA keys
     *
     * If the private key does not exists, then a new one will be generated
     *
     * @param licenseFile Input file to sign
     * @param publicKeyFile Location of the previously generated public key file
     * @param privateKeyFile Location of the previously generated private key file
     * @param signatureFile Signed, output file (eg. user's license)
     * @throws LicenseException On any error (File IO problems and CryptoAPI errors)
     */
    public void signLicense(String licenseFile, String publicKeyFile, String privateKeyFile, String signatureFile) throws LicenseException 
    {
        try 
        {
            if ( !readPrivateKey( privateKeyFile ) ) 
            {
                generateKeys();
            }
            
            initializeSignatureSign();
            
            processSourceFile(licenseFile);
            
            writeSignature(signatureFile);
            
            if ( ! readPrivateKey( privateKeyFile ) )
            {
                writePublicKey( publicKeyFile );
                writePrivateKey( privateKeyFile );
            }
        }
        
        catch ( Exception ex )
        {
            throw new LicenseException("Error in signature generation ( " + ex.getMessage() + " )");
        }
    }
}
