/* 
 *  Filename:    ApplicationViewUI 
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
    private List<ActionCategory> actions = new LinkedList();
    
    private ApplicationUI applicationUI;
    
    /**
     * active
     * 
     */
    public void active()
    {
        init();
        
        refreshContent();
    }

    /**
     * setApplicationUI
     * 
     * @param applicationUI ApplicationUI
     */
    public void setApplicationUI( ApplicationUI applicationUI )
    {
        this.applicationUI = applicationUI;
    }

    /**
     * getApplicationUI
     * 
     * @return ApplicationUI
     */
    public ApplicationUI getApplicationUI()
    {
        return applicationUI;
    }

    /**
     * addAction
     * 
     * @param label String
     * @param a Action...
     */
    protected void addAction( String label, Action... a )
    {
        actions.add( new ActionCategory( label, a ) );
    }

    /**
     * getActions
     * 
     * @return List&lt;ActionCategory&gt;
     */
    public List<ActionCategory> getActions()
    {
        return actions;
    }
    
    /**
     * getLabel
     * 
     * @return String
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * setLabel
     * 
     * @param label String
     */
    public void setLabel( String label )
    {
        this.label = label;
    }

    /**
     * getIcon
     * 
     * @return String
     */
    public String getIcon()
    {
        return icon;
    }

    /**
     * setIcon
     * 
     * @param icon String
     */
    public void setIcon( String icon )
    {
        this.icon = icon;
    }

    /**
     * init
     * 
     */
    private void init()
    {
        if ( ! init )
        {
            initComponents();
            
            init = true;
        }
    }
    
    /**
     * handleException
     * 
     * @param e Exception
     */
    protected void handleException( Exception e )
    {
        ApplicationContext.getInstance().handleException( e );
    }
    
    /**
     * refreshContent
     * 
     */
    public void refreshContent(){}
    
    
    /**
     * void
     * 
     * @return abstract
     * @ignored initComponents
     */
    protected abstract void initComponents();
}
