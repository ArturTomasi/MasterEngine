package com.mn.engcivil.interfaces.editors;

import com.mn.engcivil.application.ApplicationServices;
import com.mn.engcivil.domain.Role;
import com.mn.engcivil.domain.User;
import com.mn.engcivil.interfaces.Callback;
import com.mn.engcivil.interfaces.parts.TableLayout;
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
    public static void edit( Component owner, Callback<User> callback )
    {
        DefaultEditor editor = DefaultEditor.createEditor( owner, new UserEditor(), callback );
        editor.setWidth( "325px" );
        editor.setHeight( "400px" );
    }
    
    private User source;

    public UserEditor()
    {
        setCaption( "Editor de usuário" );
        setTitle( "Editor de usuário" );
        setInfo( "Edite as informações do usuário" );
        
        initComponents();
    }
    
    @Override
    public void setInput( User value )
    {
        this.source = value;
        
        tfPassword.setReadonly( value.getId() != null && value.getId() != 0 );
        
        btEnabled.setVisible( tfPassword.isReadonly() );
        
        tfName.setValue( value.getName() );
        tfLogin.setValue( value.getLogin() );
        
        cbProfile.setSelectedIndex( value.getProfile() );
    }

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
        
        if ( source.isSystemUser() )
        {
            if ( cbProfile.getSelectedIndex() < User.PROFILE_ADMINISTRATOR )
            {
                e.addError( "Este usuário é o Administrador do Sistema e o perfil não pode ser alterado" );
            }
        }
    }
    
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
        
        tfPassword.setType( "password" );
        tfPassword.setReadonly( true );
        
        tableLayout.addRow( lbName, tfName );
        tableLayout.addRow( lbLogin, tfLogin );
        tableLayout.addRow( lbPassword, tfPassword, btEnabled );
        tableLayout.addRow( lbProfile, cbProfile );
        
        tableLayout.setColspan( 0, 1, 2 );
        tableLayout.setColspan( 1, 1, 2 );
        tableLayout.setColspan( 3, 1, 2 );
        
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
    
    private Toolbarbutton btEnabled = new Toolbarbutton();
    
    private Combobox cbProfile = new Combobox();
}
