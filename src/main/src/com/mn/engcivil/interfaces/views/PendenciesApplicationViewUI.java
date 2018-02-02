package com.mn.engcivil.interfaces.views;

import com.mn.engcivil.application.ApplicationServices;
import com.mn.engcivil.domain.Sample;
import com.mn.engcivil.interfaces.tables.Column;
import com.mn.engcivil.interfaces.tables.SampleTable;
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
    public PendenciesApplicationViewUI()
    {
        setLabel( "Pendências" );
        setIcon( "sb_pendencies.png" );
    }
    
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
    
    @Override
    protected void initComponents()
    {
        sampleTable = new SampleTable();
        
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
