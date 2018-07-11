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
import com.me.eng.finances.ui.editors.tabs.PostingEditorAttachmentPane;
import com.me.eng.finances.ui.editors.tabs.PostingEditorGeneralPane;
import com.me.eng.finances.ui.editors.tabs.PostingEditorInformationPane;
import com.me.eng.finances.ui.editors.tabs.PostingEditorValuesPane;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
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
    public static enum Mode
    {
        FINISH( "800px", "350px" ),
        EDIT(   "800px", "450px" ),
        NEW(    "800px", "450px" );
        
        private String width, height;

        /**
         * Mode
         * 
         * @param width String
         * @param height String
         */
        private Mode( String width, String height )
        {
            this.width = width;
            this.height = height;
        }

        /**
         * getWidth
         * 
         * @return String
         */
        public String getWidth() 
        {
            return width;
        }

        /**
         * getHeight
         * 
         * @return String
         */
        public String getHeight()
        {
            return height;
        }
    }
    
    
    /**
     * edit
     * 
     * @param owner Component
     * @param mode Mode
     * @param callback Callback&lt;Posting&gt;
     */
    public static void edit( Component owner, Mode mode, Callback<Posting> callback )
    {
        PostingEditor panel = new PostingEditor();
        panel.setMode( mode );
        panel.setSource( callback.getSource() );
        
        DefaultEditor editor = DefaultEditor.createEditor( owner, panel, callback );
        editor.setWidth( mode.getWidth() );
        editor.setHeight( mode.getHeight() );
    }
    
    private Mode mode;
    private Posting source;
    
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
     * setMode
     * 
     * @param mode Mode
     */
    public void setMode( Mode mode ) 
    {
        this.mode = mode;
    }

    /**
     * setSource
     * 
     * @param source Posting
     */
    public void setSource( Posting source )
    {
        this.source = source;
    }
    
    /**
     * validateInput
     * 
     * @param e Errors
     */
    @Override
    public void validateInput( Errors e ) 
    {
        switch ( mode )
        {
            case NEW:
            case EDIT:
                generalPane.validateInput( e );
                valuesPane.validateInput( e );
                infoPane.validateInput( e );
            break;
            case FINISH:
                generalPane.validateInput( e );
                infoPane.validateInput( e );
            break;
        }   
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
        generalPane.loadField( mode );
        valuesPane.setInput( value );
        infoPane.setInput( value );
        attachmentPane.setInput( value );
        
        if ( mode == Mode.NEW )
        {
            updateValuesTab();
        }

    }

    /**
     * getInput
     * 
     * @param value Posting
     */
    @Override
    public void getInput( Posting value ) 
    {
        switch ( mode )
        {
            case NEW:
            case EDIT:
                generalPane.getInput( value );
                valuesPane.getInput( value );
                infoPane.getInput( value );
                attachmentPane.getInput( value );
            break;
            case FINISH:
                generalPane.getInput( value );
                infoPane.getInput( value );
                attachmentPane.getInput( value );
            break;
        }   
    }
    
    /**
     * updateValuesTab
     * 
     */
    private void updateValuesTab()
    {
        valuesPane.setInput( source );

        valuesTab.setVisible( mode == Mode.NEW &&  source.isRepeat() &&
                              source.getEstimateDate() != null );
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
        
        tabbox.getTabs().appendChild( generalTab );
        tabbox.getTabs().appendChild( valuesTab );
        tabbox.getTabs().appendChild( infoTab );
        tabbox.getTabs().appendChild( attachmentTab );
     
        valuesTab.setVisible( false );
        
        Tabpanel generalTabpanel = new Tabpanel();
        generalTabpanel.appendChild( generalPane );
        
        Tabpanel infoTabPanel = new Tabpanel();
        infoTabPanel.appendChild( infoPane );
        
        Tabpanel valuesTabPanel = new Tabpanel();
        valuesTabPanel.appendChild( valuesPane );
        
        Tabpanel attachmentTabPanel = new Tabpanel();
        attachmentTabPanel.appendChild( attachmentPane );
        
        tabbox.getTabpanels().appendChild( generalTabpanel );
        tabbox.getTabpanels().appendChild( valuesTabPanel );
        tabbox.getTabpanels().appendChild( infoTabPanel );
        tabbox.getTabpanels().appendChild( attachmentTabPanel );
        
        appendChild( tabbox );
        
        generalPane.addEventListener( PostingEditorGeneralPane.Events.ON_CHANGE_VALUES, (Event t) ->
        {
            source = (Posting)t.getData();
            
            updateValuesTab();
        } );
        
        valuesTab.addEventListener( Events.ON_SELECT, (Event t) ->
        {
            generalPane.getInput( source );
            valuesPane.setInput( source );
        } );
    }
    
    private Tabbox tabbox       = new Tabbox();
    private Tab generalTab      = new Tab( "Geral" );
    private Tab valuesTab       = new Tab( "Valores" );
    private Tab infoTab         = new Tab( "Informações" );
    private Tab attachmentTab   = new Tab( "Anexos" );

    private PostingEditorGeneralPane generalPane       = new PostingEditorGeneralPane();
    private PostingEditorInformationPane infoPane      = new PostingEditorInformationPane();
    private PostingEditorAttachmentPane attachmentPane = new PostingEditorAttachmentPane();
    private PostingEditorValuesPane valuesPane         = new PostingEditorValuesPane();
}