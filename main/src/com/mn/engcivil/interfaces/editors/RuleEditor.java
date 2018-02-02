package com.mn.engcivil.interfaces.editors;

import com.mn.engcivil.domain.Rule;
import com.mn.engcivil.interfaces.Callback;
import com.mn.engcivil.interfaces.parts.TableLayout;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Matheus
 */
public class RuleEditor
    extends 
        EditorPanel<Rule>
{
    public static void edit( Component owner, Callback<Rule> callback )
    {
        RuleEditor ruleEditor = new RuleEditor();
        
        DefaultEditor.createEditor( owner, ruleEditor, callback );
    }
    
    private RuleEditor()
    {
        setCaption( "Editor de Normas" );
        setTitle( "Editor de Normas" );
        setInfo( "Definir normas" );
        
        initComponents();
    }

    @Override
    public void setInput( Rule value )
    {
        tfName.setValue( value.getName() );
        tfInfo.setValue( value.getInfo() );
    }

    @Override
    public void getInput( Rule value )
    {
        value.setName( tfName.getValue() );
        value.setInfo( tfInfo.getValue() );
    }
    
    private void initComponents()
    {
        lbName.setValue( "Nome:" );
        lbInfo.setValue( "Informações:" );
        
        tfInfo.setMultiline( true );
        tfInfo.setRows( 20 );
        tfInfo.setWidth( "525px" );
        
        tfName.setHflex( "true" );
        
        tableLayout.addRow( lbName, tfName );
        tableLayout.addRow( lbInfo );
        tableLayout.addRow( tfInfo );
        
        tableLayout.setColspan( 1, 0, 2 );
        tableLayout.setColspan( 2, 0, 2 );
        
        tableLayout.setWidths( "50px" );
        
        appendChild( tableLayout );
    }
    
    private Label lbName = new Label();
    private Label lbInfo = new Label();
    
    private Textbox tfName = new Textbox();
    private Textbox tfInfo = new Textbox();
    
    private TableLayout tableLayout = new TableLayout();
}
