package com.mn.engcivil.interfaces.apps;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;

/**
 *
 * @author Matheus
 */
public abstract class Action
    implements 
        EventListener<Event>
{
    private String icon;
    private String label;
    private String tooltipText;

    public Action( String icon, String label, String tooltipText )
    {
        this.icon = icon;
        this.label = label;
        this.tooltipText = tooltipText;
    }
    
    public String getLabel()
    {
        return label;
    }

    public String getIcon()
    {
        return icon;
    }

    public String getTooltipText()
    {
        return tooltipText;
    }
}
