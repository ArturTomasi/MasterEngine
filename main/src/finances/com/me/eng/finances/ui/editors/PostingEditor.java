/*
 *  Filename:    PostingEditor
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
import com.me.eng.finances.domain.Posting;
import com.me.eng.finances.ui.editors.tabs.PostingEditorGeneralPane;
import com.me.eng.finances.ui.editors.tabs.PostingEditorInformationPane;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

/**
 *
 * @author Artur Tomasi
 */
public class PostingEditor 
    extends 
        EditorPanel<Posting>
{
    /**
     * edit
     * 
     * @param owner Component
     * @param callback Callback&lt;PostingCategory&gt;
     */
    public static void edit( Component owner, Callback<Posting> callback )
    {
        DefaultEditor editor = DefaultEditor.createEditor( owner, new PostingEditor(), callback );
        editor.setWidth( "800px" );
        editor.setHeight( "600px" );
    }

    /**
     * PostingEditor
     * 
     */
    public PostingEditor() 
    {
        setTitle( "Financeiro" );
        setInfo( "Editor de lançamento financeiro" );
        setIcon( "finances/sb_finance.png" );

        initComponents();
    }

    /**
     * validateInput
     * 
     * @param e Errors
     */
    @Override
    public void validateInput( Errors e ) 
    {
        generalPane.validateInput( e );
        infoPane.validateInput( e );
    }
    
    /**
     * setInput
     * 
     * @param value Posting
     */
    @Override
    public void setInput( Posting value ) 
    {
        generalPane.setInput( value );
        infoPane.setInput( value );
    }

    /**
     * getInput
     * 
     * @param value Posting
     */
    @Override
    public void getInput( Posting value ) 
    {
        generalPane.getInput( value );
        infoPane.getInput( value );
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        tabbox.setHflex( "true" );
        tabbox.setVflex( "true" );
        
        tabbox.appendChild( new Tabs() );
        tabbox.appendChild( new Tabpanels() );
        
        tabbox.getTabs().appendChild( new Tab( "Geral" ) );
//        tabbox.getTabs().appendChild( new Tab( "Valores" ) );
        tabbox.getTabs().appendChild( new Tab( "Informações" ) );
        
        Tabpanel generalTabpanel = new Tabpanel();
        generalTabpanel.appendChild( generalPane );
        
        Tabpanel infoTabPanel = new Tabpanel();
        infoTabPanel.appendChild( infoPane );
        
        
        tabbox.getTabpanels().appendChild( generalTabpanel );
        tabbox.getTabpanels().appendChild( infoTabPanel );
        
        appendChild( tabbox );
    }
    
    private Tabbox tabbox = new Tabbox();
     
     
    private PostingEditorGeneralPane generalPane = new PostingEditorGeneralPane();
    private PostingEditorInformationPane infoPane = new PostingEditorInformationPane();
}