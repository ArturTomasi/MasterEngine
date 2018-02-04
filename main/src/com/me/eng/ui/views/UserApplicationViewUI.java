package com.me.eng.ui.views;

import com.me.eng.application.ApplicationContext;
import com.me.eng.application.ApplicationServices;
import com.me.eng.domain.User;
import com.me.eng.ui.Callback;
import com.me.eng.ui.apps.Action;
import com.me.eng.ui.editors.UserEditor;
import com.me.eng.ui.tables.UserTable;
import com.me.eng.ui.util.Prompts;
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
    public UserApplicationViewUI()
    {
        setIcon( "sb_user.png" );
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
    
    private User getSelectedUser()
    {
        User user = userTable.getSelectedElement();
        
        if ( user == null )
        {
            Messagebox.show( "Selecione um usuário!" );
        }
        
        return user;
    }
    
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
