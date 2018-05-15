/* 
 *  Filename:    CityPicker 
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
package com.me.eng.core.ui.pickers;

import com.me.eng.core.services.ApplicationServices;
import com.me.eng.core.domain.City;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.parts.SearchField;
import com.me.eng.samples.ui.tables.CityTable;
import com.me.eng.core.ui.util.Utils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Matheus
 */
public class CityPicker
    extends 
        PickerPanel<City>
{
    /**
     * pick
     * 
     * @param owner Component
     * @param callback Callback&lt;City&gt;
     */
    public static void pick( Component owner, Callback<City> callback )
    {
        CityPicker picker = new CityPicker();
        
        DefaultPicker.createPicker( owner, picker, callback );
    }
    
    /**
     * CityPicker
     * 
     */
    public CityPicker()
    {
        initComponents();
        
        try
        {
            cityTable.setElements( ApplicationServices.getCurrent()
                                            .getCityRepository()
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
     * @param source City
     */
    @Override
    public void setSelectedItem( City source )
    {
        cityTable.setSelectedElement( source );
    }
    
    /**
     * getSelectedItem
     * 
     * @return City
     */
    @Override
    public City getSelectedItem()
    {
        return cityTable.getSelectedElement();
    }
    
    /**
     * search
     * 
     */
    private void search()
    {
        City found = Utils.search( searchField.getText(), 
                                   cityTable.getSelectedElement(), 
                                   cityTable.getElements(), 
                                   (City value) -> value.getName() );
        if ( found != null )
        {
            cityTable.setSelectedElement( found );
        }
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        cityTable.setCheckmark( true );
        
        Vlayout vlayout = new Vlayout();
        vlayout.setVflex( "true" );
        vlayout.setHflex( "true" );
        vlayout.setSpacing( "10px" );
        vlayout.appendChild( searchField );
        vlayout.appendChild( cityTable );
        
        appendChild( vlayout );
        
        searchField.addEventListener( SearchField.Events.ON_SEARCH, (Event t) ->
        {
            search();
        } );
    }
    
    private SearchField searchField = new SearchField();
    private CityTable cityTable = new CityTable();
}
