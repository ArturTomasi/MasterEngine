/* 
 *  Filename:    UserEditor 
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
package com.me.eng.core.ui.editors;

import com.me.eng.core.services.ApplicationServices;
import com.me.eng.core.domain.Role;
import com.me.eng.core.domain.User;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.parts.TableLayout;
import com.me.eng.core.ui.selectors.SectorSelector;
import org.apache.commons.codec.digest.DigestUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

/**
 *
 * @author Matheus
 */
public class UserEditor
    extends 
        EditorPanel<User>
{
    /**
     * edit
     * 
     * @param owner Component
     * @param callback Callback&lt;User&gt;
     */
    public static void edit( Component owner, Callback<User> callback )
    {
        DefaultEditor editor = DefaultEditor.createEditor( owner, new UserEditor(), callback );
        editor.setWidth( "325px" );
        editor.setHeight( "400px" );
    }
    
    private User source;

    /**
     * UserEditor
     * 
     */
    public UserEditor()
    {
        setIcon( "core/sb_user.png" );
        setTitle( "Editor de usuário" );
        setInfo( "Edite as informações do usuário" );
        
        initComponents();
    }
    
    /**
     * setInput
     * 
     * @param value User
     */
    @Override
    public void setInput( User value )
    {
        this.source = value;
        
        tfPassword.setReadonly( value.getId() != null && value.getId() != 0 );
        
        btEnabled.setVisible( tfPassword.isReadonly() );
        
        tfName.setValue( value.getName() );
        tfLogin.setValue( value.getLogin() );
        
        cbProfile.setSelectedIndex( value.getProfile() );
        cbSector.setSelectedItem( value.getSector() );
    }

    /**
     * getInput
     * 
     * @param value User
     */
    @Override
    public void getInput( User value )
    {
        value.setName( tfName.getValue() );
        value.setLogin( tfLogin.getValue() );
        
        if ( ! tfPassword.isReadonly() )
        {
            value.setPassword( DigestUtils.md5Hex( tfPassword.getValue() ) );
        }
        
        value.setProfile( cbProfile.getSelectedIndex() );
        value.setSector( cbSector.getSelectedItem() );
        
        try
        {
            Role role = ApplicationServices.getCurrent()
                                .getRoleRepository()
                                .getRole( User.PROFILE_ROLES[ value.getProfile() ] );

            value.revokeAll();
            value.grant( role );
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }

    /**
     * validateInput
     * 
     * @param e Errors
     */
    @Override
    public void validateInput( Errors e )
    {
        if ( tfName.getValue().trim().isEmpty() )
        {
            e.addError( "Nome" );
        }
        
        if ( tfLogin.getValue().trim().isEmpty() )
        {
            e.addError( "Login" );
        }
        
        else
        {
            try
            {
                User user = ApplicationServices.getCurrent()
                        .getUserRepository()
                        .findByLogin( tfLogin.getValue() );
                
                if ( user != null && ! source.equals( user ) )
                {
                    e.addError( "Login já utilizado!" );
                }
            }
            
            catch ( Exception ex )
            {
                handleException( ex );
            }
        }
        
        if ( ! tfPassword.isReadonly() )
        {
            if ( tfPassword.getValue().trim().isEmpty() )
            {
                e.addError( "Senha" );
            }
        }
        
        if ( cbProfile.getSelectedIndex() < 0 )
        {
            e.addError( "Perfil" );
        }
        
        if ( cbSector.getSelectedItem() == null )
        {
            e.addError( "Setor" );
        }
        
        if ( source.isSystemUser() )
        {
            if ( cbProfile.getSelectedIndex() < User.PROFILE_ADMINISTRATOR )
            {
                e.addError( "Este usuário é o Administrador do Sistema e o perfil não pode ser alterado" );
            }
        }
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        for ( String s : User.PROFILE_NAMES )
        {
            cbProfile.appendItem( s );
        }
        
        cbProfile.setReadonly( true );
        
        btEnabled.setLabel( "Editar" );
        
        lbName.setValue( "Nome:" );
        lbLogin.setValue( "Login:" );
        lbPassword.setValue( "Senha:" );
        lbProfile.setValue( "Perfil:" );
        lbSector.setValue( "Setor:" );
        
        tfPassword.setType( "password" );
        tfPassword.setReadonly( true );
        
        tableLayout.addRow( lbName, tfName );
        tableLayout.addRow( lbLogin, tfLogin );
        tableLayout.addRow( lbPassword, tfPassword, btEnabled );
        tableLayout.addRow( lbProfile, cbProfile );
        tableLayout.addRow( lbSector, cbSector );
        
        tableLayout.setColspan( 0, 1, 2 );
        tableLayout.setColspan( 1, 1, 2 );
        tableLayout.setColspan( 3, 1, 2 );
        tableLayout.setColspan( 4, 1, 2 );
        
        appendChild( tableLayout );
        
        btEnabled.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, new EventListener<Event>()
        {
            @Override
            public void onEvent( Event t ) throws Exception
            {
                tfPassword.setReadonly( false );
            }
        } );
    }
    
    private TableLayout tableLayout = new TableLayout();
    
    private Textbox tfLogin = new Textbox();
    private Textbox tfName = new Textbox();
    private Textbox tfPassword = new Textbox();
    
    private Label lbName = new Label();
    private Label lbLogin = new Label();
    private Label lbPassword = new Label();
    private Label lbProfile = new Label();
    private Label lbSector = new Label();
    
    private Toolbarbutton btEnabled = new Toolbarbutton();
    
    private Combobox cbProfile = new Combobox();
    private SectorSelector cbSector = new SectorSelector();
}
