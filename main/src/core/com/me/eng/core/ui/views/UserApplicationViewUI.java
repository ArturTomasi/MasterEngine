/* 
 *  Filename:    UserApplicationViewUI 
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
package com.me.eng.core.ui.views;

import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.services.ApplicationServices;
import com.me.eng.core.domain.User;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.apps.Action;
import com.me.eng.core.ui.editors.UserEditor;
import com.me.eng.core.ui.tables.UserTable;
import com.me.eng.core.ui.util.Prompts;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Matheus
 */
public class UserApplicationViewUI
    extends 
        ApplicationViewUI
{
    /**
     * UserApplicationViewUI
     * 
     */
    public UserApplicationViewUI()
    {
        setIcon( "core/sb_user.png" );
        setLabel( "Usuários" );
        
        addAction( "Usuários", addAction, editAction, deleteAction );
    }
    
    @Override
    public void refreshContent()
    {
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
     * getSelectedUser
     * 
     * @return User
     */
    private User getSelectedUser()
    {
        User user = userTable.getSelectedElement();
        
        if ( user == null )
        {
            Messagebox.show( "Selecione um usuário!" );
        }
        
        return user;
    }
    
    /**
     * addUser
     * 
     */
    private void addUser()
    {
        UserEditor.edit( this, new Callback<User>( new User() )
        {
            @Override
            public void acceptInput() throws Exception
            {
                ApplicationServices.getCurrent()
                        .getUserRepository()
                        .add( getSource() );

                userTable.addElement( getSource() );
                userTable.setSelectedElement( getSource() );
            }
        } );
    }
    
    /**
     * editUser
     * 
     */
    private void editUser()
    {
        User user = getSelectedUser();
        
        if ( user != null )
        {
            UserEditor.edit( this, new Callback<User>( user )
            {
                @Override
                public void acceptInput() throws Exception
                {
                    ApplicationServices.getCurrent()
                            .getUserRepository()
                            .update( getSource() );

                    userTable.updateElement( getSource() );
                }
            } );
        }
    }
    
    /**
     * deleteUser
     * 
     */
    private void deleteUser()
    {
        User user = getSelectedUser();
        
        if ( user != null )
        {
            if ( user.equals( ApplicationContext.getInstance().getActiveUser() ) )
            {
                Messagebox.show( "Não é possível excluir o usuário ativo!" );
                return;
            }
            
            if ( user.isSystemUser() )
            {
                Messagebox.show( "Este é o usuário Administrador e não pode ser removido!" );
                return;
            }
            
            Prompts.confirm( "Realmente excluir \"" + user + "\"?", new Callback<User>( user )
            {
                @Override
                public void acceptInput() throws Exception
                {
                    ApplicationServices.getCurrent()
                            .getUserRepository()
                            .delete( user );

                    userTable.removeElement( user );
                }
            } );
        }
    }
    
    /**
     * initComponents
     * 
     */
    @Override
    protected void initComponents()
    {
        setVflex( "true" );
        setHflex( "true" );
        
        Vlayout vlayout = new Vlayout();
        vlayout.setVflex( "true" ); 
        vlayout.setHflex( "true" ); 
        
        userTable = new UserTable();
        userTable.addContextAction( editAction );
        userTable.addContextAction( deleteAction );
        
        vlayout.appendChild( userTable );
        
        appendChild( vlayout );
    }
    
    private UserTable userTable;
    
    private Action addAction = new Action( "", "Novo", "Novo usuário" )
    {
        @Override
        public void onEvent( Event t ) throws Exception
        {
            addUser();
        }
    };
    
    private Action editAction = new Action( "", "Editar", "Editar usuário selecionad" )
    {
        @Override
        public void onEvent( Event t ) throws Exception
        {
            editUser();
        }
    };
    
    private Action deleteAction = new Action( "", "Excluir", "Excluir usuário selecionado" )
    {
        @Override
        public void onEvent( Event t ) throws Exception
        {
            deleteUser();
        }
    };
}
