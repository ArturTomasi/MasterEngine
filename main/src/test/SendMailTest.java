/* 
 *  Filename:    SendMailTest 
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

import com.me.eng.core.application.ConfigurationManager;
import com.me.eng.core.infrastructure.Mail;
import com.me.eng.core.infrastructure.SendMail;
import java.io.File;
import java.io.FileInputStream;

/**
 *
 * @author Matheus
 */
public class SendMailTest
{
    public static void main( String[] args )
    {
        try
        {
            File file = new File( "C:\\Users\\Matheus\\masterengine\\branches\\1.0.0.1-BETA\\web\\WEB-INF\\config.properties" );
            
            FileInputStream fis = new FileInputStream( file );
            
            ConfigurationManager cfg = ConfigurationManager.getInstance( fis );
            
            Mail mail = new Mail();
            mail.setSubject( "Laudo" );
            mail.setContent( "Prezados, segue laudo em anexo." );
//            mail.addAttachment( file );
            mail.addRecipient( "matt_br22@hotmail.com" );
            mail.setImageFooter( new File( "C:\\Users\\Matheus\\Desktop\\image001.png" ) );
            
            SendMail.getInstance().send( mail, cfg );
        }
        
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
}
