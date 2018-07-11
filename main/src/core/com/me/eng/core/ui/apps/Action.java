/* 
 *  Filename:    Action 
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

import com.me.eng.core.application.ResourceLocator;
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
    private String upload;

    /**
     * Action
     * 
     * @param icon String
     * @param label String
     * @param tooltipText String
     */
    public Action( String icon, String label, String tooltipText )
    {
        this.icon =  ResourceLocator.getImageResource( icon );
        this.label = label;
        this.tooltipText = tooltipText;
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
     * getIcon
     * 
     * @return String
     */
    public String getIcon()
    {
        return icon;
    }

    /**
     * getTooltipText
     * 
     * @return String
     */
    public String getTooltipText()
    {
        return tooltipText;
    }

    /**
     * setUpload
     * 
     * @param upload String
     */
    public void setUpload( String upload ) 
    {
        this.upload = upload;
    }

    /**
     * getUpload
     * 
     * @return String
     */
    public String getUpload() 
    {
        return upload;
    }
    
    /**
     * toString
     * 
     * @return String
     */
    @Override
    public String toString() 
    {
        return label;
    }
}