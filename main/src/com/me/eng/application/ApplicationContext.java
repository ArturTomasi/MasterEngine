package com.me.eng.application;

import com.google.common.base.Strings;
import com.me.eng.domain.SampleFilter;
import com.me.eng.domain.User;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Matheus
 */
public class ApplicationContext
{
    private static final ApplicationContext defaultInstance = new ApplicationContext();
    
    public static ApplicationContext getInstance()
    {
        return defaultInstance;
    }
    
    public SampleFilter getSharedSampleFilter()
    {
        SampleFilter filter = (SampleFilter) Sessions.getCurrent().getAttribute( SessionVariables.ACTIVE_SAMPLE_FILTER );
        
        if ( filter == null )
        {
            filter = new SampleFilter();
            
            Sessions.getCurrent().setAttribute( SessionVariables.ACTIVE_SAMPLE_FILTER, filter );
        }
        
        return filter;
        
    }
    
    public User getActiveUser()
    {
        User user = (User) Sessions.getCurrent().getAttribute( SessionVariables.ACTIVE_USER );
        
        if ( user == null )
        {
            Principal principal = Executions.getCurrent().getUserPrincipal();
            
            try
            {
                user = ApplicationServices.getCurrent()
                            .getUserRepository()
                            .findByLogin( principal.getName() );
                
                setAttribute( SessionVariables.ACTIVE_USER, user );
            }
            
            catch ( Exception e )
            {
                logException( e );
            }
        }
        
        return user;
    }
    
    public void setAttribute( String key, Object value )
    {
        Sessions.getCurrent().setAttribute( key, value );
    }
    
    public <T> T getAttribute( String key )
    {
        return (T) Sessions.getCurrent().getAttribute( key );
    }
    
    public void handleException( Throwable e )
    {
        logException( e );
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter( sw );
        
        e.printStackTrace( pw );
        
        String msg = Strings.isNullOrEmpty( e.getMessage() ) ? "Erro" : e.getMessage();
        
        Messagebox.show( msg, "Erro inesperado", Messagebox.OK, Messagebox.ERROR );
    }
    
    public Component getRoot()
    {
        return Executions.getCurrent().getDesktop().getFirstPage().getFirstRoot();
    }
    
    public void logInfo( String info )
    {
        Logger.getGlobal().log( Level.INFO, info );
    }
    
    public void logException( Throwable e )
    {
        Logger.getGlobal().log( Level.SEVERE, null, e );
    }
}
