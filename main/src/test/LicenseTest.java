/*
 * License Creator
 */
/*
prop = "organization=Portland\n" +
           "max_eng_samples=2\n" +
           "max_eng_recipes=3\n" +
           "max_eng_application=3\n" +
           "type=Production\n" +
           "date_start=1514772000000\n" +
           "date_end=1546221600000\n" +
           "date_created=1514772000000";
      
lincense = new java.io.File( "license.properties" );

fileWriter = new java.io.FileWriter( lincense );
fileWriter.write( prop );
fileWriter.close();

creator = new com.me.eng.license.LicenseCreator();
license = new java.io.File( "license.me" );
creator.signLicense( lincense.getAbsolutePath(), 
                                 com.me.eng.license.LicenseConstants.PUBLIC_KEY_PATH.stringValue(), 
                                 com.me.eng.license.LicenseConstants.PRIVATE_KEY_PATH.stringValue(), 
                                 license.getAbsolutePath() );

org.zkoss.zul.Filedownload.save( license, "text/plain" );
*/

/*
 * License Validator
 */
/*
validator = new com.me.eng.license.LicenseValidator();

if ( validator.verifyLicense( com.me.eng.license.LicenseConstants.PUBLIC_KEY_PATH.stringValue(),
                              com.me.eng.license.LicenseConstants.LICENSE_PATH.stringValue() ) )
{
    print( validator.getLicenseOptions() );
}

else
{
    print( "Licença inválida" );
}
*/