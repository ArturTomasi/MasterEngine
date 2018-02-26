/* 
 *  Filename:    EquipmentPicker 
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
package com.me.eng.ui.pickers;

import com.me.eng.services.ApplicationServices;
import com.me.eng.domain.Equipment;
import com.me.eng.ui.Callback;
import com.me.eng.ui.editors.EquipmentEditor;
import com.me.eng.ui.tables.EquipmentTable;
import java.util.List;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Matheus
 */
public class EquipmentPicker
    extends 
        PickerPanel<List<Equipment>>
{
    public static void pick( Component owner, Callback<List<Equipment>> callback )
    {
        DefaultPicker.createPicker( owner, new EquipmentPicker(), callback );
    }
    
    public EquipmentPicker()
    {
        setInfo( "Selecione um ou mais itens da lista abaixo" );
        
        initComponents();
        
        loadEquipments();
    }
    
    private void loadEquipments()
    {
        try
        {    
            equipmentTable.setElements( ApplicationServices.getCurrent()
                                            .getEquipmentRepository()
                                            .findAll() );
        }
        
        catch ( Exception ex )
        {
            handleException( ex );
        }
    }

    @Override
    public void setSelectedItem( List<Equipment> source )
    {
        equipmentTable.setSelectedElements( source );
    }
    
    @Override
    public List<Equipment> getSelectedItem()
    {
        return equipmentTable.getSelectedElements();
    }
    
    private void initComponents()
    {
        equipmentTable.setEditable( true );
        
        btNew.setLabel( "Novo" );
        
        Vlayout vlayout = new Vlayout();
        vlayout.setVflex( "true" );
        vlayout.appendChild( btNew );
        
        Hlayout hlayout = new Hlayout();
        hlayout.setVflex( "true" );
        hlayout.setHflex( "true" );
        hlayout.appendChild( equipmentTable );
        hlayout.appendChild( vlayout );
        
        appendChild( hlayout );
        
        equipmentTable.setMultiple( true );
        equipmentTable.setCheckmark( true );
        
        btNew.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                EquipmentEditor.edit( EquipmentPicker.this, new Callback<Equipment>( new Equipment() )
                {
                    @Override
                    public void acceptInput()
                    {
                        try
                        {
                            ApplicationServices.getCurrent()
                                    .getEquipmentRepository()
                                    .add( getSource() );
                            
                            equipmentTable.addElement( getSource() );
                        }
                        
                        catch ( Exception ex )
                        {
                            handleException( ex );
                        }
                    }
                } );
            }
        } );
    }
    
    private Toolbarbutton btNew = new Toolbarbutton();
    private EquipmentTable equipmentTable = new EquipmentTable();
}
