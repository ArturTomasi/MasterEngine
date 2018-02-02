package com.mn.engcivil.interfaces.pickers;

import com.mn.engcivil.application.ApplicationServices;
import com.mn.engcivil.domain.Capstone;
import com.mn.engcivil.interfaces.Callback;
import com.mn.engcivil.interfaces.editors.CapstoneEditor;
import com.mn.engcivil.interfaces.tables.CapstoneTable;
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
