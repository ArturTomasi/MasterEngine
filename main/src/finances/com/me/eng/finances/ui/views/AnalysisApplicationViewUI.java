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

import com.me.eng.core.ui.apps.Action;
import com.me.eng.core.ui.views.ApplicationViewUI;
import com.me.eng.finances.ui.charts.FinanceDetailsChart;
import com.me.eng.finances.ui.charts.SituationChart;
import com.me.eng.finances.ui.panes.FilterPane;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.North;

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
        financeDetailsChart.refreshContent();
        situationChart.refreshContent();
    }
    
    /**
     * initComponents
     * 
     */
    @Override
    protected void initComponents() 
    {
        setWidth( "100%" );
        setHeight( "100%" );

        borderlayout.appendChild( new North() );
        borderlayout.appendChild( new Center() );
        
        borderlayout.getNorth().setCollapsible( true );
        borderlayout.getNorth().setSplittable( true );
        
//        borderlayout.getNorth().appendChild( filterPane );
        borderlayout.getCenter().appendChild( situationChart );
        
        appendChild( borderlayout );
    }
    
    private Borderlayout borderlayout = new Borderlayout();
    
    private FinanceDetailsChart financeDetailsChart = new FinanceDetailsChart();
    private SituationChart situationChart           = new SituationChart();
//    private FilterPane filterPane                   = new FilterPane();
}
