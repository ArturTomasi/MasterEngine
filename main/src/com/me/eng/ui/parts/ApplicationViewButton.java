/* 
 *  Filename:    ApplicationViewButton 
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
package com.me.eng.ui.parts;

import com.me.eng.application.ResourceLocator;
import com.me.eng.ui.views.ApplicationViewUI;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Vbox;

/**
 *
 * @author Matheus
 */
public class ApplicationViewButton
    extends 
        Div
{
    private ApplicationViewUI viewUI;

    /**
     * ApplicationViewButton
     * 
     * @param viewUI ApplicationViewUI
     */
    public ApplicationViewButton( ApplicationViewUI viewUI )
    {
        this.viewUI = viewUI;
        
        initComponents();
    }

    /**
     * getViewUI
     * 
     * @return ApplicationViewUI
     */
    public ApplicationViewUI getViewUI()
    {
        return viewUI;
    }
    
    /**
     * setSelected
     * 
     * @param value boolean
     */
    public void setSelected( boolean value )
    {
        if ( value )
        {
            setSclass( "default-app-view-button selected" );
        }
        
        else
        {
            setSclass( "default-app-view-button" );
        }
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        setSclass( "default-app-view-button" );
        
        Label lb = new Label( viewUI.getLabel() );

        Image image = new Image( ResourceLocator.getImageResource( viewUI.getIcon() ) );
        
        Vbox vbox = new Vbox();
        vbox.setHflex( "true" );
        vbox.setVflex( "true" );
        vbox.setAlign( "middle" );
        vbox.setSpacing( "2px" );
        vbox.appendChild( image );
        vbox.appendChild( lb );
        
        appendChild( vbox );
    }
}
