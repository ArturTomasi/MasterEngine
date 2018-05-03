/* 
 *  Filename:    SampleEditorEquipmentPane 
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
package com.me.eng.samples.ui.editors.tabs;

import com.me.eng.samples.domain.Equipment;
import com.me.eng.samples.domain.Sample;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.editors.tabs.SubEditorPanel;
import com.me.eng.samples.ui.pickers.EquipmentPicker;
import com.me.eng.samples.ui.tables.EquipmentTable;
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
