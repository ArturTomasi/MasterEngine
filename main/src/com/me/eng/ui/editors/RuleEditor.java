/* 
 *  Filename:    RuleEditor 
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
package com.me.eng.ui.editors;

import com.me.eng.domain.Rule;
import com.me.eng.ui.Callback;
import com.me.eng.ui.parts.TableLayout;
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
