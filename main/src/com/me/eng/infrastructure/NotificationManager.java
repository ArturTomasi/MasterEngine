package com.me.eng.infrastructure;

import com.me.eng.application.ApplicationContext;
import com.me.eng.application.ConfigurationManager;
import com.me.eng.domain.NotificationMailBuilder;
import com.me.eng.domain.repositories.SampleRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.enterprise.inject.spi.CDI;
import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.UnboundLiteral;
import org.jboss.weld.environment.se.WeldContainer;

/**
 *
 * @author Matheus
 */
public class NotificationManager
{
    private static NotificationManager instance = new NotificationManager();
    
    public static NotificationManager getInstance()
    {
        return instance;
    }
    
    private ScheduledExecutorService scheduledExecutorService;
    
    public void start()
    {
        scheduledExecutorService = Executors.newScheduledThreadPool( 1 );
        
        Date from = new Date();
        
        Calendar c = Calendar.getInstance();
        c.add( Calendar.DAY_OF_MONTH, 1 );
        c.set( Calendar.HOUR_OF_DAY, 7 );
        c.set( Calendar.MINUTE, 0 );
        c.set( Calendar.SECOND, 0 );
        
        Date until = c.getTime();
        
        long minutes = LocalDateTime.ofInstant( from.toInstant(), ZoneId.systemDefault() )
                            .until( LocalDateTime.ofInstant( until.toInstant(), ZoneId.systemDefault() ), ChronoUnit.MINUTES );
        
        scheduledExecutorService.schedule( new Runnable()
        {
            @Override
            public void run()
            {
                sendNotifications();
            }
        }, minutes, TimeUnit.MINUTES );
//        }, 1, TimeUnit.SECONDS );
    }
    
    private void sendNotifications()
    {
        final CDI cdi = WeldContainer.current();

        SampleRepository sampleRepository = (SampleRepository) cdi.select( SampleRepository.class ).get();

        RequestContext context = (RequestContext) cdi.select( RequestContext.class, UnboundLiteral.INSTANCE ).get();
        context.activate();
        
        try
        {
            sendNotifications( sampleRepository );
        }
        
        finally
        {
            context.deactivate();
        }
    }
    
    public void sendNotifications( SampleRepository sampleRepository )
    {
        String mails[] = ConfigurationManager.getInstance().getTokens( "notification.mail" );
        
        if ( mails.length == 0 )
        {
            ApplicationContext.getInstance().logInfo( "NotificationManager: Skip notification because Empty emails" );
            return;
        }
        
        try
        {
            Mail mail = NotificationMailBuilder
                            .withSamples( sampleRepository.getSamplesToNofitication() )
                            .build();
            
            mail.addRecipient( mails );
            
            SendMail.getInstance().addQueue( mail );
        }
        
        catch ( Exception e )
        {
            ApplicationContext.getInstance().logException( e );
        }
        
        scheduledExecutorService.schedule( new Runnable()
        {
            @Override
            public void run()
            {
                sendNotifications();
            }
        }, 1, TimeUnit.DAYS );
    }
    
    public void stop()
    {
        scheduledExecutorService.shutdownNow();
    }
}
