/* 
 *  Filename:    SampleTable 
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
package com.me.eng.ui.tables;

import com.me.eng.services.ApplicationServices;
import com.me.eng.ui.tables.sample.SampleColumns;
import com.me.eng.domain.Sample;
import com.me.eng.domain.util.SampleValidator;
import com.me.eng.ui.events.EventFactory;
import com.me.eng.ui.events.EventLookup;
import com.me.eng.ui.selectors.RuptureTypeSelector;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Listcell;

/**
 *
 * @author Matheus
 */
public class SampleTable
    extends 
        AbstractTable<Sample>
{
    /**
     * SampleTable
     * 
     */
    public SampleTable()
    {
        EventFactory.subscribe( EventLookup.UPDATE_SAMPLE, this::performUpdate );
        EventFactory.subscribe( EventLookup.DELETE_SAMPLE, this::performDelete );
        
        setTableCellRenderer( (TableCellRenderer<Sample>) (Sample value, Column column, Listcell cell) -> 
        {
            String style = "font-size: 10px;";
            
            if ( ! value.getProofs().isEmpty() )
            {
                style += "font-weight: bold";
            }
            
            cell.setStyle( style );
        } );
    }
    
    /**
     * performUpdate
     * 
     * @param source Object
     */
    public void performUpdate( Object source )
    {
        if ( source instanceof Sample )
        {
            Sample s = Sample.class.cast( source );
            
            updateElement( s );

            s.getProofs().forEach( this::updateElement );
        }
    }
    
    /**
     * performDelete
     * 
     * @param source Object
     */
    public void performDelete( Object source )
    {
        if ( source instanceof Sample )
        {
            Sample sample = (Sample) source;
            
            removeElement( sample );
            
            updateElement( sample.unbound() );
        }
    }
    
    /**
     * enableInplace
     * 
     */
    public void enableInplace()
    {
        removeColumn( SampleColumns.RESISTENCE );
        
        addColumn( SampleColumns.RESISTENCE.ordinal(), new ProxyColumn<Sample>( SampleColumns.RESISTENCE )
        {
            @Override
            public Object getValueAt( Sample value )
            {
                Doublebox tf = new Doublebox();
                
                EventListener e = (EventListener) (Event t) -> 
                {
                    if ( ! SampleValidator.getInstance().isValidResistence( value, tf.getValue() ) )
                    {
                        tf.setValue( null );
                        
                        return;
                    }
                    
                    value.setResistence( tf.getValue() );
                    
                    ApplicationServices.getCurrent()
                            .getSampleRepository()
                            .update( value );
                    
                    EventFactory.publish( EventLookup.UPDATE_SAMPLE, value );
                };
                
                String style = "font-size: 10px;";
                
                if ( ! value.getProofs().isEmpty() )
                {
                    style += "font-weight: bold";
                }
                
                tf.setFormat( "###0.##" );
                tf.setStyle( style );
                tf.setConstraint( "no negative" );
                tf.setValue( value.getResistence() );
                tf.setInplace( true );
                tf.addEventListener( org.zkoss.zk.ui.event.Events.ON_OK, e );
                tf.addEventListener( org.zkoss.zk.ui.event.Events.ON_BLUR, e );
                
                return tf;
            }
        } );
        
        removeColumn( SampleColumns.TYPE_RUPTURE );
        
        addColumn( SampleColumns.TYPE_RUPTURE.ordinal(), new ProxyColumn<Sample>( SampleColumns.TYPE_RUPTURE )
        {
            @Override
            public Object getValueAt( Sample value )
            {
                RuptureTypeSelector selector = new RuptureTypeSelector();
                selector.setSelectedValue( value.getRuptureType() );
                selector.setInplace( true );
                selector.setWidth( "100%" );
                
                selector.addEventListener( org.zkoss.zk.ui.event.Events.ON_SELECT, new EventListener()
                {
                    @Override
                    public void onEvent( Event t ) throws Exception
                    {
                        value.setRuptureType( selector.getSelectedValue() );
                        
                        ApplicationServices.getCurrent()
                                .getSampleRepository()
                                .update( value );
                        
                        EventFactory.publish( EventLookup.UPDATE_SAMPLE, value );
                    }
                } );
                
                return selector;
            }

            @Override
            public String getWidth() 
            {
                return "100px";
            }
        } );
    }

    /**
     * getColumns
     * 
     * @return Column[]
     */
    @Override
    protected Column[] getColumns() 
    { 
        return SampleColumns.values(); 
    }
}
