package com.me.eng.ui.editors.tabs;

import com.me.eng.domain.Capstone;
import com.me.eng.domain.Sample;
import com.me.eng.ui.Callback;
import com.me.eng.ui.pickers.CapstonePicker;
import com.me.eng.ui.tables.CapstoneTable;
import java.util.List;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Matheus
 */
public class SampleEditorCapstonePane
    extends 
        SubEditorPanel<Sample>
{
    public SampleEditorCapstonePane()
    {
        initComponents();
    }
    
    @Override
    public void getInput( Sample source )
    {
        source.setCapstones( capstoneTable.getElements() );
    }

    @Override
    public void setInput( Sample source )
    {
        capstoneTable.setElements( source.getCapstones() );
        
//        btSelect.setDisabled( source.getParent() != null );
    }
    
    private void initComponents()
    {
        btSelect.setLabel( "Selecionar" );
        
        Vlayout vlayout = new Vlayout();
        vlayout.setVflex( "true" );
        vlayout.appendChild( btSelect );
        
        Hlayout hlayout = new Hlayout();
        hlayout.setVflex( "true" );
        hlayout.setHflex( "true" );
        hlayout.appendChild( capstoneTable );
        hlayout.appendChild( vlayout );
        
        appendChild( hlayout );
        
        btSelect.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                CapstonePicker.pick( SampleEditorCapstonePane.this, new Callback<List<Capstone>>( capstoneTable.getElements() )
                {
                    @Override
                    public void acceptInput()
                    {
                        capstoneTable.setElements( getSource() );
                    }
                } );
            }
        } );
    }
    
    private Toolbarbutton btSelect = new Toolbarbutton();
    private CapstoneTable capstoneTable = new CapstoneTable();
}
