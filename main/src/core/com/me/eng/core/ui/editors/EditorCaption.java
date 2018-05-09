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
package com.me.eng.core.ui.editors;

import com.me.eng.core.application.ResourceLocator;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
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
    private String caption, icon, info;

    /**
     * EditorCaption
     * 
     * @param icon String
     * @param caption String
     * @param info String
     */
    public EditorCaption( String icon, String caption, String info )
    {
        this.caption = caption;
        this.icon = icon;
        this.info = info;
        
        initComponents();
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        setVflex( "true" );
        setHflex( "true" );
        setStyle( "background-color: #3568d8;" );
        
        Label lbCaption = new Label( caption );
        lbCaption.setStyle( "font-weight: bold; font-size: 16px;" );
        
        Label lbInfo = new Label( info );
        lbInfo.setStyle( "color: #fff;" );
        
        Image img = new Image( ResourceLocator.getImageResource( icon ) );
        img.setStyle( "width: 45px;" );
        
        Vlayout vlayout = new Vlayout();
        vlayout.setHflex( "true" );
        vlayout.setVflex( "true" );
        vlayout.setStyle( "padding-top: 5px;" );
        vlayout.appendChild( lbCaption );
        vlayout.appendChild( lbInfo );

        
        Separator sep = new Separator( "vertical" );
        sep.setStyle( "border: 1px solid #fff; height: 45px; width: 1px; margin: 0px 5px;" );
        
        Hlayout hlayout = new Hlayout();
        hlayout.setHflex( "true" );
        hlayout.setVflex( "true" );
        hlayout.appendChild( img );
        hlayout.appendChild( sep );
        hlayout.appendChild( vlayout );
        
        appendChild( hlayout );
    }
}
