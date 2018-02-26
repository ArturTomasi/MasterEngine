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
package com.me.eng.ui.apps;

import com.me.eng.ui.views.ApplicationViewUI;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Matheus
 */
public class ApplicationUI
    extends 
        Observable
{
    private String title;
    private String label;
    private String info;
    private String icon;
    
    private LinkedList<ApplicationViewUI> views = new LinkedList();
    
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
     * setSelectedView
     * 
     * @param view ApplicationViewUI
     */
    public void setSelectedView( ApplicationViewUI view )
    {
        setChanged();
        notifyObservers( view );
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
