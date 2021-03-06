/* 
 *  Filename:    CapstonePicker 
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
package com.me.eng.samples.ui.pickers;

import com.me.eng.core.services.ApplicationServices;
import com.me.eng.samples.domain.Capstone;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.pickers.DefaultPicker;
import com.me.eng.core.ui.pickers.PickerPanel;
import com.me.eng.samples.ui.editors.CapstoneEditor;
import com.me.eng.samples.ui.tables.CapstoneTable;
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
public class CapstonePicker
    extends 
        PickerPanel<List<Capstone>>
{
    public static void pick( Component owner, Callback<List<Capstone>> callback )
    {
        DefaultPicker.createPicker( owner, new CapstonePicker(), callback );
    }
    
    public CapstonePicker()
    {
        initComponents();
        
        try
        {
            capstoneTable.setElements( ApplicationServices.getCurrent()
                                            .getCapstoneRepository()
                                            .findAll() );
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }

    @Override
    public void setSelectedItem( List<Capstone> source )
    {
        capstoneTable.setSelectedElements( source ); 
    }
    
    @Override
    public List<Capstone> getSelectedItem()
    {
        return capstoneTable.getSelectedElements();
    }
    
    private void initComponents()
    {
        capstoneTable.setEditable( true );
        
        btAdd.setLabel( "Novo" );
        
        Vlayout vlayout = new Vlayout();
        vlayout.setVflex( "true" );
        vlayout.appendChild( btAdd );
        
        Hlayout hlayout = new Hlayout();
        hlayout.setVflex( "true" );
        hlayout.setHflex( "true" );
        hlayout.appendChild( capstoneTable );
        hlayout.appendChild( vlayout );
        
        capstoneTable.setMultiple( true );
        capstoneTable.setCheckmark( true );
        
        appendChild( hlayout );
        
        btAdd.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                CapstoneEditor.edit( CapstonePicker.this, new Callback<Capstone>( new Capstone() )
                {
                    @Override
                    public void acceptInput()
                    {
                        try
                        {
                            ApplicationServices.getCurrent()
                                    .getCapstoneRepository()
                                    .add( getSource() );

                            capstoneTable.addElement( getSource() );
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
    
    private Toolbarbutton btAdd = new Toolbarbutton();
    private CapstoneTable capstoneTable = new CapstoneTable();
}
