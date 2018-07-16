/*
 *  Filename:    FinanceDetailsChart
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

import com.me.eng.finances.domain.PostingType;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.chart.AxisTitle;
import org.zkoss.chart.Charts;
import org.zkoss.chart.Options;
import org.zkoss.chart.Point;
import org.zkoss.chart.Series;
import org.zkoss.chart.Subtitle;
import org.zkoss.chart.Title;

/**
 *
 * @author Artur Tomasi
 */
public class FinanceDetailsChart 
    extends 
        Charts
{
    /**
     * FinanceDetailsChart
     * 
     */
    public FinanceDetailsChart() 
    {
        initComponents();
    }
    
    /**
     * refreshContent
     * 
     */
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
        getSeries().setName( "Lançamentos" );
        getSeries().setColorByPoint( true );
        
        List<Series> drilldowns = new ArrayList();
        
        Point costPoint = new Point( PostingType.COST.toString(), 50 );
        costPoint.setColor( "#dc3545" );
        costPoint.setDrilldown( PostingType.COST.toString() );

        Point revenuePoint = new Point( PostingType.REVENUE.toString(), 10 );
        revenuePoint.setColor( "#28a745" );
        revenuePoint.setDrilldown( PostingType.REVENUE.toString() );
        
        getSeries().addPoint( costPoint );
        getSeries().addPoint( revenuePoint );

        drilldowns.add( createSerie( PostingType.REVENUE ) );
        drilldowns.add( createSerie( PostingType.COST ) );

        getDrilldown().setSeries( drilldowns );
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
    private void initComponents()
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
                
        setHflex( "true" );
        setVflex( "true" );
        
        setTitle( title );
        setType( "column" );
        setSubtitle( subtitle );
        setOptions( options );
        
        getExporting().setEnabled( false );
        getLegend().setEnabled( false );
        getCredits().setEnabled( false );
        
        getXAxis().setType( "category" );
        getYAxis().setTitle( yTitle );
        
        getPlotOptions().getSeries().setBorderWidth( 0 );
        getPlotOptions().getSeries().getDataLabels().setEnabled( true );
        getPlotOptions().getSeries().getDataLabels().setFormat( "{point.y:.0f}%" );
        
        getTooltip().setHeaderFormat( "<span style=\"font-size:11px\">{series.name}</span><br>" );
        getTooltip().setPointFormat( "<span style=\"color:{point.color}\">{point.name}</span>: <b>{point.y:.0f}</b><br/>" );
    }
}
