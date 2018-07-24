/*
 *  Filename:    SituationChart
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
package com.me.eng.finances.ui.charts;

import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.data.ChartData;
import com.me.eng.core.services.ApplicationServices;
import com.me.eng.core.util.Formatter;
import java.util.Date;
import org.zkoss.chart.*;
import org.zkoss.chart.model.DefaultCategoryModel;

/**
 *
 * @author Artur Tomasi
 */
public class SituationChart 
    extends 
        Charts
{
    /**
     * SituationChart
     * 
     */
    public SituationChart() 
    {
        initComponents();
    }

    /**
     * refreshContent
     * 
     */
    public void refreshContent()
    {
        try
        {
            DefaultCategoryModel model = new DefaultCategoryModel();

            ApplicationServices.getCurrent().getPostingRepository().sum().forEach( data ->
            {
                model.setValue( data.get( ChartData.ATTR_CATEGORY ).toString(),
                                Formatter.getInstance().formatMonth( data.getInteger( ChartData.ATTR_SERIES ) ),
                                data.getDouble( ChartData.ATTR_VALUE ) );
                
            } );
            
            setModel( model) ;
            
            getSeries( 0 ).setColor( "#28a745" );
            getSeries( 1 ).setColor( "#dc3545" );
        }
        
        catch ( Exception e )
        {
            ApplicationContext.getInstance().handleException( e );
        }
    }
    
    /**
     * initComponents
     *  
     */
    private void initComponents()
    {
        setHflex( "true" );
        setVflex( "true" );
        
        Title title = new Title();
        title.setText( "Situação Financeira" ); 
        title.setStyle( "fontWeight: 'bold'; color: 'rgb(48, 67, 105)';" );
        
        Subtitle subtitle = new Subtitle();
        subtitle.setText( "Situação financeira de receitas e despesas agrupadas por mês" );
        subtitle.setStyle( "color: 'rgb(48, 67, 105)'" );
        
        setTitle( title );
        setSubtitle( subtitle );
        setType( "bar" );

        XAxis xAxis = getXAxis();
        xAxis.setTitle( "" );
        xAxis.getLabels().setStyle( "fontWeight: 'bold'; color: 'rgb(48, 67, 105)';" );
                
        getExporting().setEnabled( false );
        getCredits().setEnabled( false );
        
        YAxis yAxis = getYAxis();
        yAxis.setMin( 0 );
        yAxis.setTitle( "Valor total (R$)" );
        yAxis.getTitle().setStyle( "fontWeight: 'bold'; color: 'rgb(48, 67, 105)';" );
        yAxis.getTitle().setAlign( "low" );
        yAxis.getLabels().setStyle( "color: 'rgb(48, 67, 105)';" );
        
        getTooltip().setHeaderFormat( "<span style=\"font-size:14px; color:{point.color}\">{series.name}</span>" );
        getTooltip().setPointFormat( "<span style=\"color:{point.color}\">{point.name}</span>: <b>R$  {point.y:.2f}</b><br/>" );
    }
}
