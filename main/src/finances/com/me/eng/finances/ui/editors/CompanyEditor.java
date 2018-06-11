/*
 *  Filename:    CompanyEditor
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
 *  is the property of Over Line Ltda.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda,
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.finances.ui.editors;

import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.editors.DefaultEditor;
import com.me.eng.core.ui.editors.EditorPanel;
import com.me.eng.core.ui.editors.Errors;
import com.me.eng.core.ui.parts.TableLayout;
import com.me.eng.finances.domain.Company;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Artur Tomasi
 */
public class CompanyEditor 
    extends 
        EditorPanel<Company>
{
    /**
     * edit
     * 
     * @param owner Component
     * @param callback Callback&lt;Company&gt;
     */
    public static void edit( Component owner, Callback<Company> callback )
    {
        DefaultEditor editor = DefaultEditor.createEditor(owner, new CompanyEditor(), callback );
        editor.setWidth( "800px" );
        editor.setHeight( "600px" );
    }

    /**
     * CompanyEditor
     * 
     */
    public CompanyEditor() 
    {
        setTitle( "Editor de companhias" );
        setIcon( "finances/fi_company.png" );
        setInfo( "Edite as companhias de um lançamento financeiro" );
        
        initComponents();
    }
    
    /**
     * setInput
     * 
     * @param value Company
     */
    @Override
    public void setInput( Company value ) 
    {
        nameField.setValue( value.getName() );
        infoField.setValue( value.getInfo() );
        contactField.setValue( value.getContact() );
    }

    /**
     * getInput
     * 
     * @param value Company
     */
    @Override
    public void getInput( Company value ) 
    {
        value.setName( nameField.getValue() );
        value.setContact( contactField.getValue() );
        value.setInfo( infoField.getValue() );
    }

    /**
     * validateInput
     * 
     * @param e Errors
     */
    @Override
    public void validateInput( Errors e ) 
    {
        if ( nameField.getValue() == null || nameField.getValue().isEmpty() )
        {
            e.addError( "Preencha um nome!" );
        }
        
        if ( contactField.getValue() == null || contactField.getValue().isEmpty() )
        {
            e.addError( "Preencha um contato!" );
        }
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        nameField.setWidth( "100%" );
        contactField.setWidth( "100%" );
        infoField.setWidth( "100%" );
        infoField.setRows( 20 );
                
        tableLayout.addRow( lbName, nameField );
        tableLayout.addRow( lbContact, contactField );
        tableLayout.addRow( lbInfo );
        tableLayout.addRow( infoField );
        
        tableLayout.setColspan( 3, 0, 2 );
        tableLayout.setWidths( "80px", "100%" );
        
        appendChild( tableLayout );
    }
    
    private TableLayout tableLayout = new TableLayout();
    
    private Label lbName    = new Label( "Nome: " );
    private Label lbContact = new Label( "Contato: " );
    private Label lbInfo    = new Label( "Informações: " );
    
    private Textbox nameField    = new Textbox();
    private Textbox infoField    = new Textbox();
    private Textbox contactField = new Textbox();
}
