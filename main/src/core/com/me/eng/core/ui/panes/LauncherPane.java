/* 
 *  Filename:    LauncherPane 
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
package com.me.eng.core.ui.panes;

import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.West;

/**
 *
 * @author Matheus
 */
public class LauncherPane
    extends 
        Div
{ 
    public LauncherPane()
    {
        initComponents();
    }
    
    private void initComponents()
    {
        tabbox.setMold( "accordion");
        tabbox.appendChild( new Tabs() );
        tabbox.appendChild( new Tabpanels() );
        
        tabbox.getTabs().appendChild(  new Tab("Porra"));
        tabbox.getTabs().appendChild(  new Tab("Porra2"));
        tabbox.getTabs().appendChild(  new Tab("Porra3"));
        tabbox.getTabpanels().appendChild( new Tabpanel());
        tabbox.getTabpanels().appendChild( new Tabpanel());
        tabbox.getTabpanels().appendChild( new Tabpanel());
        
        appendChild( tabbox );
    }
    
    private Tabbox tabbox = new Tabbox();
    private Tabs tabs = new Tabs();
}
