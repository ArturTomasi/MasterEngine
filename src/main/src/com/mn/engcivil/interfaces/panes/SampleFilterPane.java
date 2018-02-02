package com.mn.engcivil.interfaces.panes;

import com.mn.engcivil.domain.Client;
import com.mn.engcivil.domain.SampleFilter;
import com.mn.engcivil.interfaces.parts.TableLayout;
import com.mn.engcivil.interfaces.selectors.ClientComboSelector;
import com.mn.engcivil.interfaces.selectors.JobSelector;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;

/**
 *
 * @author Matheus
 */
public class SampleFilterPane
    extends 
        Div
{
    public SampleFilterPane()
    {
        initComponents();
    }
    
    public void setFilter( SampleFilter filter )
    {
        clientComboSelector.refreshContent();
        clientComboSelector.setSelectedValue( filter.getClient() );
        jobSelector.setSelectedItem( filter.getJob() );
        jobSelector.setClient( clientComboSelector.getSelectedValue() );
        dtFrom.setValue( filter.getFrom() );
        dtUntil.setValue( filter.getUntil() );
    }

    public void getFilter( SampleFilter filter )
    {
        filter.setClient( clientComboSelector.getSelectedValue() );
        filter.setJob( jobSelector.getSelectedItem() );
        filter.setFrom( dtFrom.getValue() );
        filter.setUntil( dtUntil.getValue() );
    }
    
    public Client getSelectedClient()
    {
        return clientComboSelector.getSelectedValue();
    }
    
    public void refreshContent()
    {
        Client client = clientComboSelector.getSelectedValue();
        
        clientComboSelector.refreshContent();
        clientComboSelector.setSelectedValue( client );
    }
    
    private void initComponents()
    {
        lbClient.setValue( "Cliente:" );
        lbFrom.setValue( "Ruptura De:" );
        lbUntil.setValue( "Até:" );
        lbJob.setValue( "Obra:" );
        
        clientComboSelector.setHflex( "true" );
        
        tableLayout.addRow( lbClient, clientComboSelector );
        tableLayout.addRow( lbJob, jobSelector );
        tableLayout.addRow( lbFrom, dtFrom, lbUntil, dtUntil );
        tableLayout.setColspan( 0, 1, 3 );
        tableLayout.setColspan( 1, 1, 3 );
        
        Groupbox groupbox = new Groupbox();
        groupbox.setTitle( "Filtro" );
        groupbox.appendChild( tableLayout );
        groupbox.setOpen( false );
        groupbox.addEventListener( org.zkoss.zk.ui.event.Events.ON_OPEN, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                getParent().invalidate();
            }
        } );
        
        appendChild( groupbox );
        
        EventListener onChange = new EventListener()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                org.zkoss.zk.ui.event.Events.postEvent( org.zkoss.zk.ui.event.Events.ON_CHANGE, SampleFilterPane.this, null );
            }
        };
        
        clientComboSelector.addEventListener( org.zkoss.zk.ui.event.Events.ON_SELECT, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                jobSelector.setClient( clientComboSelector.getSelectedValue() );
                jobSelector.setSelectedItem( null );
            }
        } );
        
        clientComboSelector.addEventListener( org.zkoss.zk.ui.event.Events.ON_SELECT, onChange );
        jobSelector.addEventListener( org.zkoss.zk.ui.event.Events.ON_SELECT, onChange );
        dtFrom.addEventListener( org.zkoss.zk.ui.event.Events.ON_CHANGE, onChange );
        dtFrom.addEventListener( org.zkoss.zk.ui.event.Events.ON_OK, onChange );
        dtUntil.addEventListener( org.zkoss.zk.ui.event.Events.ON_CHANGE, onChange );
        dtUntil.addEventListener( org.zkoss.zk.ui.event.Events.ON_OK, onChange );
    }
    
    private Label lbClient = new Label();
    private Label lbFrom = new Label();
    private Label lbUntil = new Label();
    private Label lbJob = new Label();
    private Datebox dtFrom = new Datebox();
    private Datebox dtUntil = new Datebox();
    
    private ClientComboSelector clientComboSelector = new ClientComboSelector();
    private JobSelector jobSelector = new JobSelector();
    
    private TableLayout tableLayout = new TableLayout();
}
