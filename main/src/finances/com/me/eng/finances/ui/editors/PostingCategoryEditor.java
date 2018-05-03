/*
 *  Filename:    PostingCategoryEditor
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

import com.me.eng.core.domain.User;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.editors.DefaultEditor;
import com.me.eng.core.ui.editors.EditorPanel;
import com.me.eng.core.ui.editors.Errors;
import com.me.eng.core.ui.parts.TableLayout;
import com.me.eng.finances.domain.PostingCategory;
import com.me.eng.finances.domain.PostingType;
import org.zkforge.ckez.CKeditor;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Artur Tomasi
 */
public class PostingCategoryEditor 
    extends 
        EditorPanel<PostingCategory>
{
    /**
     * edit
     * 
     * @param owner Component
     * @param callback Callback&lt;PostingCategory&gt;
     */
    public static void edit( Component owner, Callback<PostingCategory> callback )
    {
        DefaultEditor editor = DefaultEditor.createEditor( owner, new PostingCategoryEditor(), callback );
        editor.setWidth( "80%" );
        editor.setHeight( "80%" );
    }

    /**
     * PostingCategoryEditor
     * 
     */
    public PostingCategoryEditor() 
    {
        setCaption( "Editor de categorias" );
        setTitle( "Editor de categorias" );
        setInfo( "Edite as categorias de um lançamento financeiro" );
        
        initComponents();
    }
    
    /**
     * setInput
     * 
     * @param value PostingCategory
     */
    @Override
    public void setInput( PostingCategory value ) 
    {
        nameField.setValue( value.getName() );
        typesField.setSelectedIndex( value.getType().ordinal() );
        infoField.setValue( value.getInfo() );
    }

    /**
     * getInput
     * 
     * @param value PostingCategory
     */
    @Override
    public void getInput( PostingCategory value ) 
    {
        value.setName( nameField.getValue() );
        value.setType( PostingType.values()[ typesField.getSelectedIndex() ] );
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
        
        if ( typesField.getSelectedIndex() < 0 )
        {
            e.addError( "Selecione um tipo!" );
        }
        
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        for ( PostingType s : PostingType.values() )
        {
            typesField.appendItem( s.toString() );
        }
        
        infoField.setAutoHeight( true );

        tableLayout.setWidths( "80px", "100%" );
        
        tableLayout.addRow( lbName, nameField );
        tableLayout.addRow( lbType, typesField );
        tableLayout.addRow( lbInfo );
        tableLayout.addRow( infoField );
        
        tableLayout.setColspan( 3, 0, 2 );
        
        appendChild( tableLayout );
    }
    
    private TableLayout tableLayout = new TableLayout();
    
    private Label lbName = new Label( "Nome: " );
    private Label lbType = new Label( "Tipo: " );
    private Label lbInfo = new Label( "Informações: " );
    
    private Textbox nameField    = new Textbox();
    private Combobox  typesField = new Combobox();
    private CKeditor infoField   = new CKeditor();
}
