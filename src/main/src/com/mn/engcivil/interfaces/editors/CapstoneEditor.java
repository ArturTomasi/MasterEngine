package com.mn.engcivil.interfaces.editors;

import com.mn.engcivil.domain.Capstone;
import com.mn.engcivil.interfaces.Callback;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Matheus
 */
public class CapstoneEditor
    extends 
        EditorPanel<Capstone>
{
    public static void edit( Component owner, Callback<Capstone> callback )
    {
        DefaultEditor editor = DefaultEditor.createEditor( owner, new CapstoneEditor(), callback );
        editor.setWidth( "400px" );
        editor.setHeight( "250px" );
    }
    
    public CapstoneEditor()
    {
        setCaption( "Editor de Tipo de Capeamento" );
        setTitle( "Editor de Tipo de Capeamento" );
        setInfo( "Definir Tipo de Capeamento" );
        
        initComponents();
    }

    @Override
    public void setInput( Capstone value )
    {
        tfName.setValue( value.getName() );
    }

    @Override
    public void getInput( Capstone value )
    {
        value.setName( tfName.getValue() );
    }
    
    private void initComponents()
    {
        lbName.setValue( "Nome:" );
        tfName.setHflex( "true" );
        
        Hbox hbox = new Hbox();
        hbox.setHflex( "true" );
        hbox.setAlign( "middle" );
        hbox.appendChild( lbName );
        hbox.appendChild( tfName );
        
        appendChild( hbox );
    }
    
    private Label lbName = new Label();
    private Textbox tfName = new Textbox();
}
