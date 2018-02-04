package test;

import com.me.eng.application.ConfigurationManager;
import com.me.eng.infrastructure.Mail;
import com.me.eng.infrastructure.SendMail;
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
