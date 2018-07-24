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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
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
        
        performFilter();
    }
    
    /**
     * performSearch
     * 
     */
    private void performFilter()
    {
        observer.notify( filter );
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
//        filter.state( sateField.getValue() );
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        addRow( new Label( "Busca" ), infoField );
        addRow( new Label( "De" ), fromField, new Label( "Até" ), untilField );
    
        fromField.addEventListener(  Events.ON_CHANGE, this::updateFilter );
        untilField.addEventListener( Events.ON_CHANGE, this::updateFilter );
        infoField.addEventListener(  Events.ON_CHANGE, this::updateFilter );
    }

    private Datebox fromField       = new Datebox();
    private Datebox untilField      = new Datebox();
    private Textbox infoField       = new Textbox();
    
}
