package com.me.eng.ui.editors;

import com.me.eng.domain.Equipment;
import com.me.eng.ui.Callback;
import com.me.eng.ui.parts.TableLayout;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Matheus
 */
public class EquipmentEditor
    extends 
        EditorPanel<Equipment>
{
    public static void edit( Component owner, Callback<Equipment> callback )
    {
        EquipmentEditor equipmentEditor = new EquipmentEditor();
        
        DefaultEditor.createEditor( owner, equipmentEditor, callback );
    }
    
    private EquipmentEditor()
    {
        setCaption( "Editor de Equipamento" );
        setTitle( "Editor de Equipamentos" );
        setInfo( "Definir Equipamento" );
        
        initComponents();
    }

    @Override
    public void setInput( Equipment value )
    {
        tfName.setValue( value.getName() );
//        tfInfo.setValue( value.getInfo() );
    }

    @Override
    public void getInput( Equipment value )
    {
        value.setName( tfName.getValue() );
//        value.setInfo( tfInfo.getValue() );
    }
    
    private void initComponents()
    {
        lbName.setValue( "Nome:" );
        lbInfo.setValue( "Informações:" );
        
        tfInfo.setVflex( "true" );
        tfInfo.setMultiline( true );
        tfInfo.setRows( 20 );
        tfInfo.setWidth( "550px" );
        
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
