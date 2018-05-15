/*
 *  Filename:    PostingCategoryPicker
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
import com.me.eng.finances.domain.PostingCategory;
import com.me.eng.finances.ui.tables.PostingCategoryTable;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Artur Tomasi
 */
public class PostingCategoryPicker 
    extends 
        PickerPanel<PostingCategory>
{
    /**
     * pick
     * 
     * @param owner Component
     * @param callback Callback&lt;PostingCategory&gt;
     */
    public static void pick( Component owner, Callback<PostingCategory> callback )
    {
        PostingCategoryPicker picker = new PostingCategoryPicker();
        picker.setTitle( "Categorias de Lançamento" );
        picker.setInfo( "Selecione um categoria de lançamento!" );
        picker.setIcon( "finances/sb_categories.png" );
        
        DefaultPicker window = DefaultPicker.createPicker( owner, picker, callback );
        window.setHeight( "550px" );
        window.setWidth( "350px" );
        
    }
    
    /**
     * PostingCategoryPicker
     * 
     */
    public PostingCategoryPicker()
    {
        initComponents();
        
        try
        {
            postingaCategoryTable.setElements( ApplicationServices.getCurrent()
                                            .getPostingCategoryRepository()
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
     * @param source PostingCategory
     */
    @Override
    public void setSelectedItem( PostingCategory source )
    {
        postingaCategoryTable.setSelectedElement( source );
    }
    
    /**
     * getSelectedItem
     * 
     * @return PostingCategory
     */
    @Override
    public PostingCategory getSelectedItem()
    {
        return postingaCategoryTable.getSelectedElement();
    }
    
    /**
     * search
     * 
     */
    private void search()
    {
        PostingCategory found = Utils.search( searchField.getText(), 
                                   postingaCategoryTable.getSelectedElement(), 
                                   postingaCategoryTable.getElements(), 
                                   (PostingCategory value) -> value.getName() );
        if ( found != null )
        {
            postingaCategoryTable.setSelectedElement( found );
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
        vlayout.appendChild( postingaCategoryTable );
        
        appendChild( vlayout );
        
        searchField.addEventListener( SearchField.Events.ON_SEARCH, (Event t) ->
        {
            search();
        } );
    }
    
    private SearchField searchField = new SearchField();
    private PostingCategoryTable postingaCategoryTable = new PostingCategoryTable();
}
