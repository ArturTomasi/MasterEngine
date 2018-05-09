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
package com.me.eng.samples.ui.editors;

import com.me.eng.samples.domain.Rule;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.editors.DefaultEditor;
import com.me.eng.core.ui.editors.EditorPanel;
import com.me.eng.core.ui.parts.TableLayout;
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
    /**
     * edit
     * 
     * @param owner Component
     * @param callback Callback&lt;Rule&gt;
     */
    public static void edit( Component owner, Callback<Rule> callback )
    {
        RuleEditor ruleEditor = new RuleEditor();
        
        DefaultEditor.createEditor( owner, ruleEditor, callback );
    }
    
    /**
     * RuleEditor
     * 
     */
    private RuleEditor()
    {
        setTitle( "Editor de Normas" );
        setInfo( "Definir normas" );
        
        initComponents();
    }

    /**
     * setInput
     * 
     * @param value Rule
     */
    @Override
    public void setInput( Rule value )
    {
        tfName.setValue( value.getName() );
        tfInfo.setValue( value.getInfo() );
    }

    /**
     * getInput
     * 
     * @param value Rule
     */
    @Override
    public void getInput( Rule value )
    {
        value.setName( tfName.getValue() );
        value.setInfo( tfInfo.getValue() );
    }
    
    /**
     * initComponents
     * 
     */
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
