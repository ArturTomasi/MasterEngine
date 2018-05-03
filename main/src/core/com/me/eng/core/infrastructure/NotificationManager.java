/* 
 *  Filename:    NotificationManager 
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
package com.me.eng.core.infrastructure;

import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.application.ConfigurationManager;
import com.me.eng.samples.domain.NotificationMailBuilder;
import com.me.eng.samples.repositories.SampleRepository;
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
    
    /**
     * getInstance
     * 
     * @return NotificationManager
     */
    public static NotificationManager getInstance()
    {
        return instance;
    }
    
    private ScheduledExecutorService scheduledExecutorService;
    
    /**
     * start
     * 
     */
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
    }
    
    /**
     * sendNotifications
     * 
     */
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
    
    /**
     * sendNotifications
     * 
     * @param sampleRepository SampleRepository
     */
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
    
    /**
     * stop
     * 
     */
    public void stop()
    {
        scheduledExecutorService.shutdownNow();
    }
}
