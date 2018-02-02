package com.mn.engcivil.interfaces.editors.tabs;

import com.mn.engcivil.application.ApplicationServices;
import com.mn.engcivil.domain.User;
import com.mn.engcivil.interfaces.editors.Errors;
import com.mn.engcivil.interfaces.parts.TableLayout;
import org.apache.commons.codec.digest.DigestUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

/**
 *
 * @author Matheus
 */
public class UserEditorGeneralPane
    extends 
        SubEditorPanel<User>
{
    private User source;

    public UserEditorGeneralPane()
    {
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
    }
    
    private void initComponents()
    {
        btEnabled.setLabel( "Editar" );
        
        lbName.setValue( "Nome:" );
        lbLogin.setValue( "Login:" );
        lbPassword.setValue( "Senha:" );
        
        tfPassword.setType( "password" );
        tfPassword.setReadonly( true );
        
        tableLayout.addRow( lbName, tfName );
        tableLayout.addRow( lbLogin, tfLogin );
        tableLayout.addRow( lbPassword, tfPassword, btEnabled );
        
        tableLayout.setColspan( 0, 1, 2 );
        tableLayout.setColspan( 1, 1, 2 );
        
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
    
    private Toolbarbutton btEnabled = new Toolbarbutton();
}
