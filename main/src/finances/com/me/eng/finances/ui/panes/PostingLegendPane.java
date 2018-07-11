/*
 *  Filename:    PostingLegendPane
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
 *  is the property of Over Line Ltda.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda,
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.finances.ui.panes;

import com.me.eng.core.application.ResourceLocator;
import com.me.eng.finances.domain.PostingState;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;

/**
 *
 * @author Artur Tomasi
 */
public class PostingLegendPane 
    extends 
        Hbox
{
    /**
     * PostingLegendPane
     * 
     */
    public PostingLegendPane() 
    {
        initComponents();
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        setSclass( "posting-legend-pane" );
        setHflex( "true" );
        
        appendChild( new Image( ResourceLocator.getImageResource( PostingState.REGISTRED.icon() ) ) );
        appendChild( new Label( PostingState.REGISTRED.toString() ) );
        
        appendChild( new Image( ResourceLocator.getImageResource( PostingState.PROGRESS.icon() ) ) );
        appendChild( new Label( PostingState.PROGRESS.toString() ) );
        
        appendChild( new Image( ResourceLocator.getImageResource( PostingState.FINISHED.icon() ) ) );
        appendChild( new Label( PostingState.FINISHED.toString() ) );
    }
}
