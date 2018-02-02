package com.mn.engcivil.interfaces.tables;

import com.mn.engcivil.application.ApplicationServices;
import com.mn.engcivil.interfaces.tables.sample.SampleColumns;
import com.mn.engcivil.domain.Sample;
import com.mn.engcivil.domain.util.SampleValidator;
import com.mn.engcivil.interfaces.selectors.RuptureTypeSelector;
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
    public SampleTable()
    {
        setTableCellRenderer( new TableCellRenderer<Sample>()
        {
            @Override
            public void render( Sample value, Column column, Listcell cell )
            {
                String style = "font-size: 10px;";
                
                if ( ! value.getProofs().isEmpty() )
                {
                    style += "font-weight: bold";
                }
                
                cell.setStyle( style );
            }
        } );
    }
    
    public void enableInplace()
    {
        removeColumn( SampleColumns.RESISTENCE );
        
        addColumn( SampleColumns.RESISTENCE.ordinal(), new ProxyColumn<Sample>( SampleColumns.RESISTENCE )
        {
            @Override
            public Object getValueAt( Sample value )
            {
                Doublebox tf = new Doublebox();
                
                EventListener e = new EventListener()
                {
                    @Override
                    public void onEvent( Event t ) throws Exception
                    {
                        if ( ! SampleValidator.getInstance().isValidResistence( value, tf.getValue() ) )
                        {
                            return;
                        }
                        
                        value.setResistence( tf.getValue() );
                        
                        ApplicationServices.getCurrent()
                                .getSampleRepository()
                                .update( value );
                        
                        updateElement( value );
                    }
                };
                
                String style = "font-size: 10px;";
                
                if ( ! value.getProofs().isEmpty() )
                {
                    style += "font-weight: bold";
                }
                
                tf.setFormat( "#,##0.##" );
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
                        
                        updateElement( value );
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

    @Override
    protected Column[] getColumns() 
    { 
        return SampleColumns.values(); 
    }
}
