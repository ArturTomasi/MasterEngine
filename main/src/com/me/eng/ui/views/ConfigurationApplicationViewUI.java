package com.me.eng.ui.views;

import com.me.eng.application.ConfigurationManager;
import com.me.eng.ui.apps.Action;
import com.me.eng.ui.parts.ConfigurationTree;
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
