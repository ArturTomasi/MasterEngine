/*
 *  Filename:    CompanyPicker
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
 *  is the property of Over Line Ltda.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda,
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.finances.ui.pickers;

import com.me.eng.core.services.ApplicationServices;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.parts.SearchField;
import com.me.eng.core.ui.pickers.DefaultPicker;
import com.me.eng.core.ui.pickers.PickerPanel;
import com.me.eng.core.ui.util.Utils;
import com.me.eng.finances.domain.Company;
import com.me.eng.finances.ui.tables.CompanyTable;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Artur Tomasi
 */
public class CompanyPicker 
    extends 
        PickerPanel<Company>
{
    /**
     * pick
     * 
     * @param owner Component
     * @param callback Callback&lt;CompletionType&gt;
     */
    public static void pick( Component owner, Callback<Company> callback )
    {
        DefaultPicker picker = DefaultPicker.createPicker( owner, new CompanyPicker(), callback );
        picker.setTitle( "Companhias" );
        picker.setInfo( "Selecione a companhia do lançamento finaceiro!" );
        picker.setIcon( "finances/fi_company.png" );
        picker.setHeight( "400px" );
    }
    
    /**
     * CompanyPicker
     * 
     */
    public CompanyPicker()
    {
        initComponents();
        
        try
        {
            companyTable.setElements( ApplicationServices.getCurrent().getCompanyRepository().findAll() );
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    /**
     * setSelectedItem
     * 
     * @param source Company
     */
    @Override
    public void setSelectedItem( Company source )
    {
        companyTable.setSelectedElement( source );
    }
    
    /**
     * getSelectedItem
     * 
     * @return Company
     */
    @Override
    public Company getSelectedItem()
    {
        return companyTable.getSelectedElement();
    }
    
    /**
     * search
     * 
     */
    private void search()
    {
        Company found = Utils.search( searchField.getText(), 
                                   companyTable.getSelectedElement(), 
                                   companyTable.getElements(), 
                                   (Company value) -> value.toString() );
        if ( found != null )
        {
            companyTable.setSelectedElement( found );
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
        vlayout.appendChild( companyTable );
        
        appendChild( vlayout );
        
        searchField.addEventListener( SearchField.Events.ON_SEARCH, (Event t) -> search() );
        
        companyTable.addEventListener( Events.ON_SELECT, (Event t ) -> close() );
    }
    
    private SearchField searchField = new SearchField();
    private CompanyTable companyTable = new CompanyTable();
}
