/*
 *  Filename:    CompletionTypePicker
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

import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.parts.SearchField;
import com.me.eng.core.ui.pickers.DefaultPicker;
import com.me.eng.core.ui.pickers.PickerPanel;
import com.me.eng.core.ui.util.Utils;
import com.me.eng.finances.domain.CompletionType;
import com.me.eng.finances.ui.lists.CompletionTypeList;
import java.util.Arrays;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Artur Tomasi
 */
public class CompletionTypePicker 
    extends 
        PickerPanel<CompletionType>
{
    /**
     * pick
     * 
     * @param owner Component
     * @param callback Callback&lt;CompletionType&gt;
     */
    public static void pick( Component owner, Callback<CompletionType> callback )
    {
        CompletionTypePicker picker = new CompletionTypePicker();
        picker.setTitle( "Tipos de Finalização" );
        picker.setInfo( "Selecione o tipo de finalização do lançamento finaceiro!" );
        picker.setIcon( "finances/fi_completion_type.png" );
        
        DefaultPicker.createPicker( owner, picker, callback ).setHeight( "400px" );
    }
    
    /**
     * CompletionTypePicker
     * 
     */
    public CompletionTypePicker()
    {
        initComponents();
        
        try
        {
            typesList.setElements( Arrays.asList( CompletionType.values() ) );
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }

    /**
     * setSelectedItem
     * 
     * @param source CompletionType
     */
    @Override
    public void setSelectedItem( CompletionType source )
    {
        typesList.setSelectedElement( source );
    }
    
    /**
     * getSelectedItem
     * 
     * @return CompletionType
     */
    @Override
    public CompletionType getSelectedItem()
    {
        return typesList.getSelectedElement();
    }
    
    /**
     * search
     * 
     */
    private void search()
    {
        CompletionType found = Utils.search( searchField.getText(), 
                                   typesList.getSelectedElement(), 
                                   typesList.getElements(), 
                                   (CompletionType value) -> value.toString() );
        if ( found != null )
        {
            typesList.setSelectedElement( found );
        }
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        Vlayout vlayout = new Vlayout();
        vlayout.setSpacing( "10px" );
        vlayout.appendChild( searchField );
        vlayout.appendChild( typesList );
        
        appendChild( vlayout );
        
        searchField.addEventListener( SearchField.Events.ON_SEARCH, (Event t) -> search() );
    }
    
    private SearchField searchField = new SearchField();
    private CompletionTypeList typesList = new CompletionTypeList();
}
