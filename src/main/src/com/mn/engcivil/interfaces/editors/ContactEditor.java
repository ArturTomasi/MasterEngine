package com.mn.engcivil.interfaces.editors;

import com.mn.engcivil.domain.Contact;
import com.mn.engcivil.interfaces.Callback;
import com.mn.engcivil.interfaces.parts.TableLayout;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Matheus
 */
public class ContactEditor
    extends 
        EditorPanel<Contact>
{
    public static void edit( Component owner, Callback<Contact> callback )
    {
        DefaultEditor editor = DefaultEditor.createEditor( owner, new ContactEditor(), callback );
        editor.setWidth( "300px" );
        editor.setHeight( "300px" );
    }
    
    public ContactEditor()
    {
        setTitle( "Editor de Contatos" );
        setCaption( "Editor de Contatos" );
        setInfo( "Edita as propriedades do Contato" );
        
        initComponents();
    }
    
    @Override
    public void setInput( Contact value )
    {
        tfName.setValue( value.getName() );
        tfTelephone.setValue( value.getTelephone() );
        tfMail.setValue( value.getEmail() );
    }

    @Override
    public void getInput( Contact value )
    {
        value.setName( tfName.getValue() );
        value.setTelephone( tfTelephone.getValue() );
        value.setEmail( tfMail.getValue() );
    }
    
    private void initComponents()
    {
        lbName.setValue( "Nome:" );
        lbTelephone.setValue( "Telefone:" );
        lbMail.setValue( "Email:" );
        
        tfTelephone.setClientAttribute( "data-mask", "(99) 9999-99999" );
        
        tableLayout.addRow( lbName, tfName );
        tableLayout.addRow( lbMail, tfMail );
        tableLayout.addRow( lbTelephone, tfTelephone );
        
        appendChild( tableLayout );
    }
    
    private Textbox tfName = new Textbox();
    private Textbox tfTelephone = new Textbox();
    private Textbox tfMail = new Textbox();
    
    private Label lbName = new Label();
    private Label lbTelephone = new Label();
    private Label lbMail = new Label();
    
    private TableLayout tableLayout = new TableLayout();
}
