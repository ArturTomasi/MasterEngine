/* 
 *  Filename:    EditorCaption 
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
package com.me.eng.ui.editors;

import org.zkoss.zul.Div;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Matheus
 */
public class EditorCaption
    extends 
        Div
{
    private String caption;
    private String icon;
    private String info;

    public EditorCaption( String icon, String caption, String info )
    {
        this.caption = caption;
        this.icon = icon;
        this.info = info;
        
        initComponents();
    }
    
    private void initComponents()
    {
        setVflex( "true" );
        setHflex( "true" );
        
        Label lbCaption = new Label( caption );
        lbCaption.setStyle( "font-weight: bold;" );
        
        Label lbInfo = new Label( info );
        lbInfo.setStyle( "color: #64728E; margin-left: 5px" );
        
        Vlayout vlayout = new Vlayout();
        vlayout.setHflex( "true" );
        vlayout.setVflex( "true" );
        vlayout.appendChild( lbCaption );
        vlayout.appendChild( lbInfo );
        vlayout.setStyle( "margin-left: 25px; margin-top: 5px" );
        
        appendChild( vlayout );
    }
}
