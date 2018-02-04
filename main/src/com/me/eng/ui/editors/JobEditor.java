package com.me.eng.ui.editors;

import com.me.eng.domain.Job;
import com.me.eng.ui.Callback;
import com.me.eng.ui.editors.tabs.JobEditorContactPane;
import com.me.eng.ui.editors.tabs.JobEditorGeneralPane;
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
