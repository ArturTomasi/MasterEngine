package com.mn.engcivil.interfaces.views;

import com.mn.engcivil.application.ConfigurationManager;
import com.mn.engcivil.interfaces.apps.Action;
import com.mn.engcivil.interfaces.parts.ConfigurationTree;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Matheus
 */
public class ConfigurationApplicationViewUI
    extends 
        ApplicationViewUI
{
    public ConfigurationApplicationViewUI()
    {
        setIcon( "sb_setup.png" );
        setLabel( "Configurações" );
        
        addAction( "Conifgurações", saveAction );
    }
    
    @Override
    public void refreshContent()
    {
        try
        {
            configurationTree.refreshContent();
        }
        
        catch ( Exception ex )
        {
            handleException( ex );
        }
    }
    
    private void save()
    {
        try
        {
            ConfigurationManager.getInstance()
                    .store( configurationTree.getConfigurationNodeRoot() );
            
            Messagebox.show( "Salvo com sucesso!" );
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    @Override
    protected void initComponents()
    {
        appendChild( configurationTree );
    }
    
    private ConfigurationTree configurationTree = new ConfigurationTree();

        
    private Action saveAction = new Action( "", "Salvar", "Salvar configurações" )
    {
        @Override
        public void onEvent( Event t ) throws Exception
        {
            save();
        }
    };
}
