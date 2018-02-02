package com.mn.engcivil.application;

import com.mn.engcivil.infrastructure.NotificationManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Matheus
 */
public class Bootstrap
    implements 
        ServletContextListener
{
    @Override
    public void contextInitialized( ServletContextEvent sce )
    {
        NotificationManager.getInstance().start();
    }

    @Override
    public void contextDestroyed( ServletContextEvent sce )
    {
        NotificationManager.getInstance().stop();
    }
}
