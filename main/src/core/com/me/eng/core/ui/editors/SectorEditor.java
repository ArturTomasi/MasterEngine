/*
 * Filename:    SectorEditor 
 *
 * Author:      Artur Tomasi
 * EMail:       tomasi.artur@gmail.com
 * Internet:    https://www.masterengine.com.br
 *
 * Copyright © 2019 by Over Line Ltda.
 * 95900-038, LAJEADO, RS
 * BRAZIL
 *
 * The copyright to the computer program(s) herein
 * is the property of Over Line Ltda., Brazil.
 * The program(s) may be used and/or copied only with
 * the written permission of Over Line Ltda.
 * or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which
 * the program(s) have been supplied.
 */
package com.me.eng.core.ui.editors;

import com.me.eng.core.domain.Sector;
import com.me.eng.core.services.ApplicationServices;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.parts.TableLayout;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Artur Tomasi
 */
public class SectorEditor 
    extends 
        EditorPanel<Sector>
{
    /**
     * edit
     * 
     * @param owner Component
     * @param callback Callback&lt;Sector&gt;
     */
    public static void edit( Component owner, Callback<Sector> callback )
    {
        DefaultEditor editor = DefaultEditor.createEditor( owner, new SectorEditor(), callback );
        editor.setWidth( "400px" );
        editor.setHeight( "200px" );
    }
    
    private Sector source;

    /**
     * SectorEditor
     * 
     */
    public SectorEditor()
    {
        setIcon( "core/sb_sector.png" );
        setTitle( "Editor de setor" );
        setInfo( "Edite as informações do setor" );
        
        initComponents();
    }
    
    /**
     * setInput
     * 
     * @param value Sector
     */
    @Override
    public void setInput( Sector value )
    {
        this.source = value;
        
        tfName.setValue( value.getName() );
    }

    /**
     * getInput
     * 
     * @param value User
     */
    @Override
    public void getInput( Sector value )
    {
        value.setName( tfName.getValue() );
    }

    /**
     * validateInput
     * 
     * @param e Errors
     */
    @Override
    public void validateInput( Errors e )
    {
        if ( tfName.getValue().trim().isEmpty() )
        {
            e.addError( "Nome" );
        }
        
        else
        {
            try
            {
                Sector sector = ApplicationServices.getCurrent()
                        .getSectorRepository()
                        .findByName( tfName.getValue() );
                
                if ( sector != null && ! source.equals( sector ) )
                {
                    e.addError( "Setor já existe!" );
                }
            }
            
            catch ( Exception ex )
            {
                handleException( ex );
            }
        }
    }
    
    /**
     * initComponents
     * 
     */
    private void initComponents()
    {
        lbName.setValue( "Nome:" );
        tfName.setWidth( "100%" );
        
        tableLayout.setStyle( "width: 100%" );
        tableLayout.addRow( lbName, tfName );
        
        appendChild( tableLayout );
    }
    
    private TableLayout tableLayout = new TableLayout();
    
    private Textbox tfName = new Textbox();
    private Label lbName = new Label();
}
