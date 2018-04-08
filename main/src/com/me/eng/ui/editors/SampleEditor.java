/* 
 *  Filename:    SampleEditor 
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

import com.me.eng.domain.Sample;
import com.me.eng.ui.Callback;
import com.me.eng.ui.editors.tabs.SampleEditorCapstonePane;
import com.me.eng.ui.editors.tabs.SampleEditorEquipmentPane;
import com.me.eng.ui.editors.tabs.SampleEditorGeneralPane;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

/**
 *
 * @author Matheus
 */
public class SampleEditor
    extends 
        EditorPanel<Sample>
{
    /**
     * edit
     * 
     * @param owner Component
     * @param callback Callback&lt;Sample&gt;
     * @param showProofs boolean
     */
    public static void edit( Component owner, Callback<Sample> callback, boolean showProofs )
    {
        DefaultEditor.createEditor( owner, new SampleEditor( showProofs ), callback );
    }
    
    private boolean showProofs;
    
    /**
     * SampleEditor
     * 
     * @param showProofs boolean
     */
    public SampleEditor( boolean showProofs )
    {
        this.showProofs = showProofs;
        
        setTitle( "Editor de Amostras" );
        setCaption( "Editor de Amostras" );
        setInfo( "Edite as propriedades da Amostra" );
        
        initComponents();
    }
    
    /**
     * setInput
     * 
     * @param value Sample
     */
    @Override
    public void setInput( Sample value )
    {
        generalPane.setShowProofs( showProofs );
        generalPane.setInput( value );
        equipmentPane.setInput( value );
        capstonePane.setInput( value );
    }

    /**
     * getInput
     * 
     * @param value Sample
     */
    @Override
    public void getInput( Sample value )
    {
        generalPane.getInput( value );
        equipmentPane.getInput( value );
        capstonePane.getInput( value );
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
        equipmentPane.validateInput( e );
        capstonePane.validateInput( e );
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
        tabbox.getTabs().appendChild( new Tab( "Tipo de Capeamento" ) );
        tabbox.getTabs().appendChild( new Tab( "Equipamentos" ) );
        
        Tabpanel generalTabpanel = new Tabpanel();
        generalTabpanel.appendChild( generalPane );
        
        Tabpanel equipmentTabpanel = new Tabpanel();
        equipmentTabpanel.appendChild( equipmentPane );
        
        Tabpanel capstoneTabpanel = new Tabpanel();
        capstoneTabpanel.appendChild( capstonePane );
        
        tabbox.getTabpanels().appendChild( generalTabpanel );
        tabbox.getTabpanels().appendChild( capstoneTabpanel );
        tabbox.getTabpanels().appendChild( equipmentTabpanel );
        
        appendChild( tabbox );
    }
    
    private Tabbox tabbox = new Tabbox();
    
    private SampleEditorGeneralPane generalPane = new SampleEditorGeneralPane();
    private SampleEditorEquipmentPane equipmentPane = new SampleEditorEquipmentPane();
    private SampleEditorCapstonePane capstonePane = new SampleEditorCapstonePane();
}
