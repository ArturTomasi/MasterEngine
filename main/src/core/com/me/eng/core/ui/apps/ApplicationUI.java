/* 
 *  Filename:    ApplicationUI 
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
package com.me.eng.core.ui.apps;

import com.me.eng.core.ui.util.GenericObserver;
import com.me.eng.core.ui.views.ApplicationViewUI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Matheus
 */
public class ApplicationUI
{
    public static final int ACTIVE_VIEW   = 0;
    public static final int UPDATE_ACTION = 1;
    
    private String title;
    private String label;
    private String info;
    private String icon;
    
    private LinkedList<ApplicationViewUI> views = new LinkedList();
    
    private Map<Integer, GenericObserver> observers = new HashMap();
    
    /**
     * addView
     * 
     * @param viewUI ApplicationViewUI
     */
    protected void addView( ApplicationViewUI viewUI )
    {
        views.add( viewUI );
        viewUI.setApplicationUI( this );
    }
    
    /**
     * getFirstView
     * 
     * @return ApplicationViewUI
     */
    public ApplicationViewUI getFirstView()
    {
        return views.getFirst();
    }
    
    /**
     * addObserver
     * 
     * @param id int
     * @param obs GenericObserver
     */
    public void addObserver( int id, GenericObserver obs )
    {
        observers.put( id, obs );
    }
    
    /**
     * setSelectedView
     * 
     * @param view ApplicationViewUI
     */
    public void setSelectedView( ApplicationViewUI view )
    {
        GenericObserver obs = observers.get( ACTIVE_VIEW );
        
        if ( obs != null )
        {
            obs.notify( view );
        }
    }
    
    /**
     * updateActions
     * 
     */
    public void updateActions()
    {
        GenericObserver obs = observers.get( UPDATE_ACTION );
        
        if ( obs != null )
        {
            obs.notify( getViews() );
        }
    }
    
    /**
     * getViews
     * 
     * @return List&lt;ApplicationViewUI&gt;
     */
    public List<ApplicationViewUI> getViews()
    {
        return views;
    }

    /**
     * getTitle
     * 
     * @return String
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * setTitle
     * 
     * @param title String
     */
    public void setTitle( String title )
    {
        this.title = title;
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
     * getInfo
     * 
     * @return String
     */
    public String getInfo()
    {
        return info;
    }

    /**
     * setInfo
     * 
     * @param info String
     */
    public void setInfo( String info )
    {
        this.info = info;
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
}
