/*
 *  Filename:    AnalysisApplicationViewUI
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
package com.me.eng.finances.ui.views;

import com.me.eng.core.ui.views.ApplicationViewUI;
import com.me.eng.finances.domain.PostingType;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.chart.*;

/**
 *
 * @author Artur Tomasi
 */
public class AnalysisApplicationViewUI 
    extends 
        ApplicationViewUI
{

    /**
     * AnalysisApplicationViewUI
     * 
     */
    public AnalysisApplicationViewUI() 
    {
        setLabel( "Análises" );
        setIcon( "finances/sb_analysis.png" );
    }

    /**
     * refreshContent
     * 
     */
    @Override
    public void refreshContent() 
    {
        initSeries();
    }
    
    /**
     * initSeries
     * 
     */
    private void initSeries()
    {
        chart.getSeries().setName( "Lançamentos" );
        chart.getSeries().setColorByPoint( true );
        
        List<Series> drilldowns = new ArrayList();
        
        Point costPoint = new Point( PostingType.COST.toString(), 50 );
        costPoint.setColor( "red" );
        costPoint.setDrilldown( PostingType.COST.toString() );

        Point revenuePoint = new Point( PostingType.REVENUE.toString(), 10 );
        revenuePoint.setColor( "green" );
        revenuePoint.setDrilldown( PostingType.REVENUE.toString() );
        
        chart.getSeries().addPoint( costPoint );
        chart.getSeries().addPoint( revenuePoint );

        drilldowns.add( createSerie( PostingType.REVENUE ) );
        drilldowns.add( createSerie( PostingType.COST ) );

        chart.getDrilldown().setSeries( drilldowns );
    }
    
    /**
     * createSerie
     * 
     * @param type PostingType
     * @return Series
     */
    private Series createSerie( PostingType type )
    {
        Series series = new Series( type.toString() );
        
        series.addPoint( new Point( type.icon(), 10 ) );
        
        return series;
    }
    
    /**
     * initComponents
     * 
     */
    @Override
    protected void initComponents() 
    {
        Title title = new Title();
        title.setText( "Lançamentos da Categoria" ); 
        title.setStyle( "fontWeight: 'bold'; color: 'rgb(48, 67, 105)';" );
        
        Subtitle subtitle = new Subtitle();
        subtitle.setText( "Quantidade de lançamentos por Situação" );
        subtitle.setStyle( "color: 'rgb(48, 67, 105)'" );
        
        AxisTitle yTitle = new AxisTitle();
        yTitle.setText( "Quantidade" );
        yTitle.setStyle( "fontWeight: 'bold'; color: 'rgb(48, 67, 105)';" );
                
        Options options = new Options();
        options.getLang().setDrillUpText( "◁ Voltar para {series.name}" );
                
        chart.setHflex( "1" );
        chart.setVflex( "1" );
        
        chart.setTitle( title );
        chart.setType( "column" );
        chart.setSubtitle( subtitle );
        chart.setOptions( options );
        
        chart.getExporting().setEnabled( false );
        chart.getLegend().setEnabled( false );
        chart.getCredits().setEnabled( false );
        
        chart.getXAxis().setType( "category" );
        chart.getYAxis().setTitle( yTitle );
        
        chart.getPlotOptions().getSeries().setBorderWidth( 0 );
        chart.getPlotOptions().getSeries().getDataLabels().setEnabled( true );
        chart.getPlotOptions().getSeries().getDataLabels().setFormat( "{point.y:.0f}%" );
        
        chart.getTooltip().setHeaderFormat( "<span style=\"font-size:11px\">{series.name}</span><br>" );
        chart.getTooltip().setPointFormat( "<span style=\"color:{point.color}\">{point.name}</span>: <b>{point.y:.0f}</b><br/>" );
        
        appendChild( chart );
    }

    private Charts chart = new Charts();
}
