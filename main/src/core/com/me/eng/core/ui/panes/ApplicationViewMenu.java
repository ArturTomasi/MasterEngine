/* 
 *  Filename:    ApplicationViewMenu 
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

import com.me.eng.core.ui.apps.ApplicationUI;
import com.me.eng.core.ui.parts.ApplicationViewButton;
import org.zkoss.zkex.zul.Fisheyebar;

/**
 *
 * @author Matheus
 */
public class ApplicationViewMenu
    extends 
        Fisheyebar
{
    private ApplicationViewButton selectedButton;
    
    /**
     * ApplicationViewMenu
     * 
     */
    public ApplicationViewMenu()
    {
        setSclass( "fisheye-application-menu" );
        setAttachEdge( "bottom" );
        setLabelEdge( "top" );
        
        setItemHeight( 65 );
        setItemMaxHeight( 125 );
        setItemWidth( 65 );
        setItemMaxWidth( 125 );
    }
    
    /**
     * setApplicationUI
     * 
     * @param ui ApplicationUI
     */
    public void setApplicationUI( final ApplicationUI ui )
    {
        if ( ui.getViews().size() != 1 )
        {
            ui.getViews().forEach( viewUI -> 
            {
                final ApplicationViewButton bt = new ApplicationViewButton( viewUI );

                bt.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, e ->
                {
                    selectedButton.setSelected( false );
                    selectedButton = bt;
                    selectedButton.setSelected( true );

                    ui.setSelectedView( viewUI );
                } );

                appendChild( bt );

                if ( selectedButton == null )
                {
                    selectedButton = bt;
                    selectedButton.setSelected( true );
                }
            } );
        }
        
        else
        {
            setVisible( false );
        }
    }
}
