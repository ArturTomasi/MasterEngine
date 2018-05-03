/* 
 *  Filename:    ClientEditor 
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

import com.me.eng.core.domain.Client;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.editors.DefaultEditor;
import com.me.eng.core.ui.editors.EditorPanel;
import com.me.eng.core.ui.editors.Errors;
import com.me.eng.samples.ui.editors.tabs.ClientEditorContactPane;
import com.me.eng.samples.ui.editors.tabs.ClientEditorGeneralPane;
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
public class ClientEditor
    extends 
        EditorPanel<Client>
{
    public static void edit( Component owner, Callback<Client> callback )
    {
        DefaultEditor.createEditor( owner, new ClientEditor(), callback );
    }
    
    public ClientEditor()
    {
        setTitle( "Editor de Clientes" );
        setCaption( "Editor de Clientes" );
        setInfo( "Edite as propriedades do Cliente" );
        
        initComponents();
    }
    
    @Override
    public void setInput( Client value )
    {
        generalPane.setInput( value );
        contactPane.setInput( value );
    }

    @Override
    public void getInput( Client value )
    {
        generalPane.getInput( value );
        contactPane.getInput( value );
    }

    @Override
    public void validateInput( Errors e )
    {
        generalPane.validateInput( e );
        contactPane.validateInput( e );
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
    
    private ClientEditorGeneralPane generalPane = new ClientEditorGeneralPane();
    private ClientEditorContactPane contactPane = new ClientEditorContactPane();
}
