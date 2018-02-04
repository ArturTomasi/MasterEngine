package com.me.eng.ui.apps;

import com.me.eng.ui.views.ApplicationViewUI;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import org.zkoss.zk.ui.event.Events;

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
    
    private LinkedList<ApplicationViewUI> views = new LinkedList<ApplicationViewUI>();
    
    protected void addView( ApplicationViewUI viewUI )
    {
        views.add( viewUI );
        viewUI.setApplicationUI( this );
    }
    
    public ApplicationViewUI getFirstView()
    {
        return views.getFirst();
    }
    
    public void setSelectedView( ApplicationViewUI view )
    {
        setChanged();
        notifyObservers( view );
    }
    
    public List<ApplicationViewUI> getViews()
    {
        return views;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel( String label )
    {
        this.label = label;
    }

    public String getInfo()
    {
        return info;
    }

    public void setInfo( String info )
    {
        this.info = info;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon( String icon )
    {
        this.icon = icon;
    }
}
