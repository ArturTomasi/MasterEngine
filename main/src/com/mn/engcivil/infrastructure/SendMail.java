package com.mn.engcivil.infrastructure;

import com.mn.engcivil.application.ApplicationContext;
import com.mn.engcivil.application.ConfigurationManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.enterprise.inject.spi.CDI;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.UnboundLiteral;
import org.jboss.weld.environment.se.WeldContainer;

/**
 *
 * @author Matheus
 */
public class SendMail
{
    private static final SendMail instance = new SendMail();
    
    private volatile boolean stop;
    
    private ExecutorService executorService;
    
    public static SendMail getInstance()
    {
        return instance;
    }

    public SendMail()
    {
        final CDI cdi = WeldContainer.current();
        
        int cores = Math.max( Runtime.getRuntime().availableProcessors() - 1, 1 );
        
        ApplicationContext.getInstance().logInfo( "SendMail: Create " + cores + " threads" );
        
        executorService = Executors.newFixedThreadPool( cores );
        
        for ( int index = 0; index < cores; index++ )
        {
            executorService.submit( new Runnable()
            {
                @Override
                public void run()
                {
                    waitForMail( cdi );
                }
            } );
        }
    }
    
    private Collection<Mail> sendingMails = new ConcurrentLinkedDeque<Mail>();
    private BlockingQueue<Mail> queue = new LinkedBlockingDeque<Mail>();
    
    public void close()
    {
        stop = true;
        queue.clear();
    }
    
    public boolean hasEmails()
    {
        return queue.size() > 0 || sendingMails.size() > 0;
    }
    
    public boolean hasPendingMailBySource( Object source )
    {
        List<Mail> list = new ArrayList( sendingMails.size() + queue.size() );
        list.addAll( sendingMails );
        list.addAll( queue );
        
        for ( Mail mail : list )
        {
            if ( mail.getSource() != null && source.equals( mail.getSource() ) )
            {
                return true;
            }
        }
        
        return false;
    }
    
    public void addQueue( Mail mail ) throws Exception
    {
        queue.put( mail );
        
        ApplicationContext.getInstance().logInfo( "SendMail: Add to queue: " + mail.getSubject() );
    }
    
    public void send( Mail mail ) throws Exception
    {
        ConfigurationManager cfg = ConfigurationManager.getInstance();
        
        send( mail, cfg );
    }
    
    public void send( Mail mail, final ConfigurationManager cfg ) throws Exception
    {
        if ( mail.getRecipients().isEmpty() )
        {
            throw new IllegalStateException( "Recipient list is empty" );
        }
        
        String from = cfg.getProperty( "sendmail.from" );
        
        Properties props = new Properties();
        props.setProperty( "mail.smtp.host", cfg.getProperty( "sendmail.host" ) );
        props.setProperty( "mail.smtp.port", cfg.getProperty( "sendmail.port" ) );
        props.setProperty( "mail.smtp.starttls.enable", cfg.getProperty( "sendmail.starttls" ) );
        
        for ( int index = 0; index < cfg.getInt( "sendmail.count" ); index++ )
        {
            props.put( cfg.getProperty( "sendmail." + index + ".name" ), 
                       cfg.getProperty( "sendmail." + index + ".value" ) );
        }
        
        Authenticator auth = null;
        
        if ( cfg.getFlag( "sendmail.auth" ) )
        {
            auth = new Authenticator()
            {
                @Override
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication( cfg.getProperty( "sendmail.user" ), 
                                                       cfg.getDecryptedProperty( "sendmail.pwd" ) );
                }
            };
            
            props.setProperty( "mail.smtp.auth", "true" );
        }
        
        Session session = Session.getInstance( props, auth );
        
        if ( cfg.getFlag( "sendmail.debug" ) )
        {
            session.setDebug( true );
        }

        MimeMessage mimeMessage = new MimeMessage( session );
        mimeMessage.setSubject( mail.getSubject() );
        mimeMessage.setFrom( new InternetAddress( from ) );

        List<Address> recipients = new ArrayList<Address>( mail.getRecipients().size() );
        
        for ( String email : mail.getRecipients() )
        {
            recipients.add( new InternetAddress( email ) );
        }
        
        mimeMessage.setRecipients( Message.RecipientType.TO, recipients.toArray( new Address[ recipients.size() ] ) );
        
        Multipart mp = new MimeMultipart();
        
        String content = mail.getContent();
        
        MimeBodyPart body = new MimeBodyPart();
        
        if ( mail.getImageFooter() != null )
        {
            content += "<br><img src=\"cid:image_footer\">";
            
            MimeBodyPart footer = new MimeBodyPart();
            footer.setDisposition( Part.INLINE );
            footer.setContentID( "<image_footer>" );
            footer.setDataHandler( new DataHandler( new FileDataSource( mail.getImageFooter() ) ) );
            footer.setFileName( mail.getImageFooter().getName() );
            
            mp.addBodyPart( footer );
        }
        
        body.setContent( content, "text/html" );

        mp.addBodyPart( body );
        
        if ( ! mail.getAttachments().isEmpty() )
        {
            for ( File file : mail.getAttachments() )
            {
                MimeBodyPart attachment = new MimeBodyPart();
                attachment.setDisposition( Part.ATTACHMENT );
                attachment.setDataHandler( new DataHandler( new FileDataSource( file ) ) );
                attachment.setFileName( file.getName() );
                attachment.setHeader( "Content-Transfer-Encoding", "base64" );
                
                mp.addBodyPart( attachment );
            }
        }
        
        mimeMessage.setContent( mp );
        mimeMessage.setSentDate( new Date() );
        
        Transport.send( mimeMessage );
    }
    
    private void waitForMail( CDI cdi )
    {
        RequestContext context = (RequestContext) cdi.select( RequestContext.class, UnboundLiteral.INSTANCE ).get();

        context.activate();

        while ( ! stop )
        {
            try
            {
                Mail mail = queue.take();

                sendingMails.add( mail );

                try
                {
                    mail.setStatus( Mail.Status.SENDING );

                    send( mail );

                    mail.setStatus( Mail.Status.SUCCESS );
                    
                    ApplicationContext.getInstance().logInfo( "SendMail: Sent " + mail.getSubject() );
                }

                catch ( Exception e )
                {
                    ApplicationContext.getInstance().logException( e );

                    mail.setException( e );
                    mail.setStatus( Mail.Status.FAIL );
                }

                finally
                {
                    sendingMails.remove( mail );
                }
            }

            catch ( InterruptedException e )
            {
                ApplicationContext.getInstance().logException( e );
            }
        }

        context.deactivate();
    }
}
