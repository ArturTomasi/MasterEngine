package com.me.eng.ui.editors;

import com.me.eng.domain.Client;
import com.me.eng.ui.Callback;
import com.me.eng.ui.editors.tabs.ClientEditorContactPane;
import com.me.eng.ui.editors.tabs.ClientEditorGeneralPane;
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
