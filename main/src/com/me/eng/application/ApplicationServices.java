package com.me.eng.application;

import org.zkoss.zk.ui.Executions;

/**
 *
 * @author Matheus
 */
public class ApplicationServices
{
    public static ApplicationService getCurrent()
    {
        return (ApplicationService) Executions.getCurrent().getSession().getAttribute( SessionVariables.APPLICATION_SERVICE );
    }
    
    static void setCurrent( ApplicationService service )
    {
        Executions.getCurrent().getSession().setAttribute( SessionVariables.APPLICATION_SERVICE, service );
    }
}
