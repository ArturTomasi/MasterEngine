/* 
 *  Filename:    ApplicationCaption 
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
package com.me.eng.ui.panes;

import com.me.eng.application.ConfigurationManager;
import com.me.eng.application.ResourceLocator;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;

/**
 *
 * @author Matheus
 */
public class ApplicationCaption
    extends 
        Div
{
    private String logo = ResourceLocator.getImageResource( ConfigurationManager.getInstance().getProperty( "ApplicationCaption.logo", "logo.png" ) );
    
    /**
     * ApplicationCaption
     * 
     */
    public ApplicationCaption()
    {
        initComponents();
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        setHflex( "true" );
        setStyle( "position: relative" );
        
        Image companyLogo = new Image( logo );
        companyLogo.setHeight( "50px" );
        companyLogo.setStyle( "position: absolute; right: 10px; top: 10px" );
        
        Image meLogo = new Image( ResourceLocator.getImageResource( "me_logo.png" ) );
        meLogo.setHeight( "50px" );
        
        Label meLabel = new Label( "Master Engine" );
        meLabel.setStyle( "color: white; font-size: 30px" );
        
        Hbox hlayout = new Hbox();
        hlayout.setAlign( "middle" );
        hlayout.setSpacing( "10px" );
        hlayout.appendChild( meLogo );
        hlayout.appendChild( meLabel );
        hlayout.setStyle( "position: absolute; left: 10px; top: 10px;" );
        
        appendChild( hlayout );
        appendChild( companyLogo );
    }
}
