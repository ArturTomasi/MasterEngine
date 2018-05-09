/* 
 *  Filename:    CapstoneEditor 
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

import com.me.eng.samples.domain.Capstone;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.editors.DefaultEditor;
import com.me.eng.core.ui.editors.EditorPanel;
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
    /**
     * edit
     * 
     * @param owner Component
     * @param callback Callback&lt;Capstone&gt;
     */
    public static void edit( Component owner, Callback<Capstone> callback )
    {
        DefaultEditor editor = DefaultEditor.createEditor( owner, new CapstoneEditor(), callback );
        editor.setWidth( "400px" );
        editor.setHeight( "250px" );
    }
    
    /**
     * CapstoneEditor
     * 
     */
    public CapstoneEditor()
    {
        setTitle( "Editor de Tipo de Capeamento" );
        setInfo( "Definir Tipo de Capeamento" );
        
        initComponents();
    }

    /**
     * setInput
     * 
     * @param value Capstone
     */
    @Override
    public void setInput( Capstone value )
    {
        tfName.setValue( value.getName() );
    }

    /**
     * getInput
     * 
     * @param value Capstone
     */
    @Override
    public void getInput( Capstone value )
    {
        value.setName( tfName.getValue() );
    }
    
    /**
     * initComponents
     * 
     */
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
