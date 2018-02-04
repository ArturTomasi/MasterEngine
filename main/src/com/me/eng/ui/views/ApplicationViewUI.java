package com.me.eng.ui.views;

import com.me.eng.application.ApplicationContext;
import com.me.eng.ui.apps.Action;
import com.me.eng.ui.apps.ActionCategory;
import com.me.eng.ui.apps.ApplicationUI;
import java.util.LinkedList;
import java.util.List;
import org.zkoss.zul.Div;

/**
 *
 * @author Matheus
 */
public abstract class ApplicationViewUI
    extends 
        Div
{
    private boolean init;
    
    private String label;
    private String icon;
    private List<ActionCategory> actions = new LinkedList<ActionCategory>();
    
    private ApplicationUI applicationUI;
    
    public void active()
    {
        init();
        refreshContent();
    }

    public void setApplicationUI( ApplicationUI applicationUI )
    {
        this.applicationUI = applicationUI;
    }

    public ApplicationUI getApplicationUI()
    {
        return applicationUI;
    }
    
    public void refreshContent(){}
    
    protected void addAction( String label, Action... a )
    {
        actions.add( new ActionCategory( label, a ) );
    }

    public List<ActionCategory> getActions()
    {
        return actions;
    }
    
    public String getLabel()
    {
        return label;
    }

    public void setLabel( String label )
    {
        this.label = label;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon( String icon )
    {
        this.icon = icon;
    }

    private void init()
    {
        if ( ! init )
        {
            initComponents();
            
            init = true;
        }
    }
    
    protected void handleException( Exception e )
    {
        ApplicationContext.getInstance().handleException( e );
    }
    
    protected abstract void initComponents();
}
