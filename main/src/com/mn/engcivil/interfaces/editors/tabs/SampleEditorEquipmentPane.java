package com.mn.engcivil.interfaces.editors.tabs;

import com.mn.engcivil.domain.Equipment;
import com.mn.engcivil.domain.Sample;
import com.mn.engcivil.interfaces.Callback;
import com.mn.engcivil.interfaces.pickers.EquipmentPicker;
import com.mn.engcivil.interfaces.tables.EquipmentTable;
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
public class SampleEditorEquipmentPane
    extends 
        SubEditorPanel<Sample>
{
    public SampleEditorEquipmentPane()
    {
        initComponents();
    }
    
    @Override
    public void getInput( Sample source )
    {
        source.setEquipaments( equipmentTable.getElements() );
    }

    @Override
    public void setInput( Sample source )
    {
        equipmentTable.setElements( source.getEquipaments() );
        
//        btSelect.setDisabled( source.getParent() != null );
    }
    
    private void initComponents()
    {
        btSelect.setLabel( "Selecionar" );
        
        Vlayout vlayout = new Vlayout();
        vlayout.setVflex( "true" );
        vlayout.appendChild( btSelect );
        vlayout.appendChild( btRemove );
        
        Hlayout hlayout = new Hlayout();
        hlayout.setVflex( "true" );
        hlayout.setHflex( "true" );
        hlayout.appendChild( equipmentTable );
        hlayout.appendChild( vlayout );
        
        appendChild( hlayout );
        
        btSelect.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                EquipmentPicker.pick( SampleEditorEquipmentPane.this, new Callback<List<Equipment>>( equipmentTable.getElements() )
                {
                    @Override
                    public void acceptInput()
                    {
                        equipmentTable.setElements( getSource() );
                    }
                } );
            }
        } );
    }
    
    private Toolbarbutton btSelect = new Toolbarbutton();
    private Toolbarbutton btRemove = new Toolbarbutton();
    private EquipmentTable equipmentTable = new EquipmentTable();
}
