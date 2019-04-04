/*
 * Filename:    SectorPicker 
 *
 * Author:      Artur Tomasi
 * EMail:       tomasi.artur@gmail.com
 * Internet:    https://www.masterengine.com.br
 *
 * Copyright © 2019 by Over Line Ltda.
 * 95900-038, LAJEADO, RS
 * BRAZIL
 *
 * The copyright to the computer program(s) herein
 * is the property of Over Line Ltda., Brazil.
 * The program(s) may be used and/or copied only with
 * the written permission of Over Line Ltda.
 * or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which
 * the program(s) have been supplied.
 */
package com.me.eng.core.ui.pickers;

import com.me.eng.core.domain.Sector;
import com.me.eng.core.services.ApplicationServices;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.parts.SearchField;
import com.me.eng.core.ui.tables.SectorTable;
import com.me.eng.core.ui.util.Utils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Artur Tomasi
 */
public class SectorPicker 
    extends 
        PickerPanel<Sector>
{
    /**
     * pick
     * 
     * @param owner Component
     * @param callback Callback&lt;Sector&gt;
     */
    public static void pick( Component owner, Callback<Sector> callback )
    {
        DefaultPicker picker = DefaultPicker.createPicker( owner, new SectorPicker(), callback );
        picker.setTitle( "Setores" );
        picker.setInfo( "Selecione um setor!" );
        picker.setIcon( "core/sb_sector.png" );
        picker.setHeight( "350px" );
        picker.setWidth( "450px" );
    }
    
    /**
     * SectorPicker
     * 
     */
    public SectorPicker()
    {
        initComponents();
        
        try
        {
            userTable.setElements( ApplicationServices.getCurrent()
                                            .getSectorRepository()
                                            .findAll() );
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }

    /**
     * setSelectedItem
     * 
     * @param source Sector
     */
    @Override
    public void setSelectedItem( Sector source )
    {
        userTable.setSelectedElement( source );
    }
    
    /**
     * getSelectedItem
     * 
     * @return Sector
     */
    @Override
    public Sector getSelectedItem()
    {
        return userTable.getSelectedElement();
    }
    
    /**
     * search
     * 
     */
    private void search()
    {
        Sector found = Utils.search( searchField.getText(), 
                                   userTable.getSelectedElement(), 
                                   userTable.getElements(), 
                                   (Sector value) -> value.getName() );
        if ( found != null )
        {
            userTable.setSelectedElement( found );
        }
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        Vlayout vlayout = new Vlayout();
        vlayout.setVflex( "true" );
        vlayout.setHflex( "true" );
        vlayout.setSpacing( "10px" );
        vlayout.appendChild( searchField );
        vlayout.appendChild( userTable );
        
        appendChild( vlayout );
        
        searchField.addEventListener( SearchField.Events.ON_SEARCH, (Event t) ->
        {
            search();
        } );
        
        userTable.addEventListener( Events.ON_SELECT, (Event t ) -> close() );
    }
    
    private SearchField searchField = new SearchField();
    private SectorTable userTable = new SectorTable();
}
