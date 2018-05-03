/* 
 *  Filename:    PendenciesApplicationViewUI 
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
package com.me.eng.samples.ui.views;

import com.me.eng.core.services.ApplicationServices;
import com.me.eng.samples.domain.Sample;
import com.me.eng.core.ui.tables.Column;
import com.me.eng.samples.ui.tables.SampleTable;
import com.me.eng.core.ui.views.ApplicationViewUI;
import org.zkoss.zul.Label;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Matheus
 */
public class PendenciesApplicationViewUI
    extends 
        ApplicationViewUI
{
    /**
     * PendenciesApplicationViewUI
     * 
     */
    public PendenciesApplicationViewUI()
    {
        setLabel( "Pendências" );
        setIcon( "samples/sb_pendencies.png" );
    }
    
    /**
     * refreshContent
     * 
     */
    @Override
    public void refreshContent()
    {
        try
        {
            sampleTable.setElements( ApplicationServices.getCurrent()
                                         .getSampleRepository()
                                         .getSamplesToNofitication() ); 
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    /**
     * initComponents
     * 
     */
    @Override
    protected void initComponents()
    {
        sampleTable = new SampleTable();
        sampleTable.enableInplace();
        
        sampleTable.addColumn( 0, new Column<Sample>()
        {
            @Override
            public String getLabel()
            {
                return "Previsão para ruptura";
            }

            @Override
            public Object getValueAt( Sample value )
            {
                long days = value.daysToRupture();
                
                String label = value.daysToRupture() + " dias";
                
                if ( days == 0 )
                {
                    label = "hoje";
                }
                
                Label lb = new Label( label );
                lb.setStyle( "font-size: 18px; text-align: center;" );
                return lb;
            }
        } );
        
        setVflex( "true" );
        setHflex( "true" );
        
        Vlayout vlayout = new Vlayout();
        vlayout.setHflex( "true" );
        vlayout.setVflex( "true" );
        
        vlayout.appendChild( sampleTable );
        
        appendChild( vlayout );
    }
    
    private SampleTable sampleTable;
}
