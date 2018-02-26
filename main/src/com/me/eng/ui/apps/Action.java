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
package com.me.eng.ui.apps;

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
