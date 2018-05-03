/* 
 *  Filename:    JobEditor 
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

import com.me.eng.samples.domain.Job;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.editors.DefaultEditor;
import com.me.eng.core.ui.editors.EditorPanel;
import com.me.eng.core.ui.editors.Errors;
import com.me.eng.samples.ui.editors.tabs.JobEditorContactPane;
import com.me.eng.samples.ui.editors.tabs.JobEditorGeneralPane;
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
public class JobEditor
    extends 
        EditorPanel<Job>
{
    public static void edit( Component owner, Callback<Job> callback )
    {
        DefaultEditor editor = DefaultEditor.createEditor( owner, new JobEditor(), callback );
        editor.setWidth( "400px" );
        editor.setHeight( "600px" );
    }
    
    public JobEditor()
    {
        setCaption( "Editor de Obras" );
        setTitle( "Editor de Obras" );
        setInfo( "Definir Obra" );
        
        initComponents();
    }

    @Override
    public void setInput( Job value )
    {
        generalPane.setInput( value );
        contactPane.setInput( value );
    }

    @Override
    public void getInput( Job value )
    {
        generalPane.getInput( value );
        contactPane.getInput( value );
    }

    @Override
    public void validateInput( Errors e )
    {
        generalPane.validateInput( e );
    }
    
    private void initComponents()
    {
        tabbox.setHflex( "true" );
        tabbox.setVflex( "true" );
        
        tabbox.appendChild( new Tabs() );
        tabbox.appendChild( new Tabpanels() );
        
        tabbox.getTabs().appendChild( new Tab( "Geral" ) );
        tabbox.getTabs().appendChild( new Tab( "Contato" ) );
        
        Tabpanel generalTabpanel = new Tabpanel();
        generalTabpanel.appendChild( generalPane );
        
        Tabpanel equipmentTabpanel = new Tabpanel();
        equipmentTabpanel.appendChild( contactPane );
        
        tabbox.getTabpanels().appendChild( generalTabpanel );
        tabbox.getTabpanels().appendChild( equipmentTabpanel );
        
        appendChild( tabbox );
    }
    
    private Tabbox tabbox = new Tabbox();
    
    private JobEditorGeneralPane generalPane = new JobEditorGeneralPane();
    private JobEditorContactPane contactPane = new JobEditorContactPane();
}
