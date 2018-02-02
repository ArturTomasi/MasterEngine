package com.mn.engcivil.application;

import org.zkoss.zk.ui.Executions;

/**
 *
 * @author Matheus
 */
public class ApplicationServices
{
    public static ApplicationService getCurrent()
    {
        return (ApplicationService) Executions.getCurrent().getSession().getAttribute( 
                "com.mn.engcivil.application.ApplicationService" );
    }
    
    static void setCurrent( ApplicationService service )
    {
        Executions.getCurrent().getSession().setAttribute( "com.mn.engcivil.application.ApplicationService", service );
    }
}
