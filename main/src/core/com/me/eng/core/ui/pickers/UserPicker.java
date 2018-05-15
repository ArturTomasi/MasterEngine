/*
 *  Filename:    UserPicker
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
package com.me.eng.core.ui.pickers;

import com.me.eng.core.domain.User;
import com.me.eng.core.services.ApplicationServices;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.parts.SearchField;
import com.me.eng.core.ui.tables.UserTable;
import com.me.eng.core.ui.util.Utils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Artur Tomasi
 */
public class UserPicker 
    extends 
        PickerPanel<User>
{
    /**
     * pick
     * 
     * @param owner Component
     * @param callback Callback&lt;User&gt;
     */
    public static void pick( Component owner, Callback<User> callback )
    {
        UserPicker picker = new UserPicker();
        picker.setTitle( "Usuários" );
        picker.setInfo( "Selecione um usuário!" );
        picker.setIcon( "core/sb_user.png" );
        
        DefaultPicker.createPicker( owner, picker, callback ).setHeight( "350px" );
        
    }
    
    /**
     * UserPicker
     * 
     */
    public UserPicker()
    {
        initComponents();
        
        try
        {
            userTable.setElements( ApplicationServices.getCurrent()
                                            .getUserRepository()
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
     * @param source User
     */
    @Override
    public void setSelectedItem( User source )
    {
        userTable.setSelectedElement( source );
    }
    
    /**
     * getSelectedItem
     * 
     * @return User
     */
    @Override
    public User getSelectedItem()
    {
        return userTable.getSelectedElement();
    }
    
    /**
     * search
     * 
     */
    private void search()
    {
        User found = Utils.search( searchField.getText(), 
                                   userTable.getSelectedElement(), 
                                   userTable.getElements(), 
                                   (User value) -> value.getName() );
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
    }
    
    private SearchField searchField = new SearchField();
    private UserTable userTable = new UserTable();
}
