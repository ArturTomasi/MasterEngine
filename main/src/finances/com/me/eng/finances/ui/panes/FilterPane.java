/*
 *  Filename:    FilterPane
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

import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.ui.parts.Datebox;
import com.me.eng.core.ui.parts.TableLayout;
import com.me.eng.core.ui.util.GenericObserver;
import com.me.eng.finances.domain.PostingFilter;
import com.me.eng.finances.ui.selectors.FilterModeSelector;
import com.me.eng.finances.ui.selectors.PostingStateSelector;
import com.me.eng.finances.ui.selectors.PostingTypeSelector;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Center;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Artur Tomasi
 */
public class FilterPane 
    extends 
        TableLayout
{
    private GenericObserver<PostingFilter> observer;
    
    private PostingFilter filter = new PostingFilter();
    
    /**
     * FilterPane
     * 
     * @param observer GenericObserver&lt;PostingFilter&gt;
     */
    public FilterPane( GenericObserver<PostingFilter> observer ) 
    {
        this.observer = observer;
        
        loadDefault();
     
        initComponents();
    }
    
    /**
     * getFilter
     * 
     * @return PostingFilter
     */
    public PostingFilter getFilter()
    {
        return filter;
    }
    
    /**
     * loadDefault
     * 
     */
    private void loadDefault()
    {
        filter.user( ApplicationContext.getInstance().getActiveUser() );
        filter.mode( PostingFilter.Mode.PENDENCY );
        
        loadInput();
    }
    
    /**
     * setInput
     * 
     */
    private void loadInput()
    {
        fromField.setValue( filter.from() );
        untilField.setValue( filter.until() );
        infoField.setValue( filter.info() );
        typeSelector.setSelectedElement( filter.type() );
        modeSelector.setSelectedElement( filter.mode() );
        stateSelector.setSelectedElement( filter.state() );
        
        performFilter();
    }
    
    /**
     * performSearch
     * 
     */
    private void performFilter()
    {
        observer.notify( filter );
        
        if ( getParent() != null )
        {
            getChildren().get( 1 ).setVisible( filter.mode() == PostingFilter.Mode.CUSTOM );
            getChildren().get( 2 ).setVisible( filter.mode() == PostingFilter.Mode.CUSTOM );

            getParent().invalidate();
        }
    }
    
    /**
     * updateFilter
     * 
     */
    private void updateFilter( Event e )
    {
        filter.from( fromField.getValue() );
        filter.until( untilField.getValue() );
        filter.info( infoField.getValue() );
        filter.type( typeSelector.getSelectedElement() );
        filter.mode( modeSelector.getSelectedElement() );
        filter.state( stateSelector.getSelectedElement() );
        
        performFilter();
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        setStyle( "padding: 5px; ");
        
        modeSelector.setWidth( "643px" );
        infoField.setWidth( "300px" );
        typeSelector.setWidth( "100%" );
        stateSelector.setWidth( "100%" );
        
        setWidths( "60px", "300px", "60px", "150px", "40px", "150px" );
        
        addRow( new Label( "Modo: " ),  modeSelector );
        addRow( new Label( "Busca: " ), infoField,     new Label( "De: " ),     fromField, new Label( "Até: " ), untilField  );
        addRow( new Label( "Tipo: " ),  typeSelector,  new Label( "Situação: " ),stateSelector );
    
        setColspan( 0, 1, 5 );
        setColspan( 2, 3, 3 );
        
        fromField.addEventListener(  Events.ON_CHANGE, this::updateFilter );
        untilField.addEventListener( Events.ON_CHANGE, this::updateFilter );
        infoField.addEventListener(  Events.ON_CHANGE, this::updateFilter );
        
        stateSelector.addEventListener( Events.ON_SELECT, this::updateFilter );
        typeSelector.addEventListener(  Events.ON_SELECT, this::updateFilter );
        modeSelector.addEventListener(  Events.ON_SELECT, this::updateFilter );
    }

    private Datebox fromField       = new Datebox();
    private Datebox untilField      = new Datebox();
    private Textbox infoField       = new Textbox();
    
    private PostingStateSelector stateSelector  = new PostingStateSelector();
    private PostingTypeSelector typeSelector    = new PostingTypeSelector();
    private FilterModeSelector modeSelector     = new FilterModeSelector();
}
