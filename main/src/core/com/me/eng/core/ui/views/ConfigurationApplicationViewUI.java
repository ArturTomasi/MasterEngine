/* 
 *  Filename:    ConfigurationApplicationViewUI 
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
package com.me.eng.core.ui.views;

import com.me.eng.core.application.ConfigurationManager;
import com.me.eng.core.ui.apps.Action;
import com.me.eng.core.ui.parts.ConfigurationTree;
import com.me.eng.core.ui.util.Prompts;
import org.zkoss.zk.ui.event.Event;

/**
 *
 * @author Matheus
 */
public class ConfigurationApplicationViewUI
    extends 
        ApplicationViewUI
{
    /**
     * ConfigurationApplicationViewUI
     * 
     */
    public ConfigurationApplicationViewUI()
    {
        setIcon( "core/sb_setup.png" );
        setLabel( "Configurações" );
        
        addAction( "Conifgurações", saveAction );
    }
    
    /**
     * refreshContent
     * 
     */
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
    
    /**
     * save
     * 
     */
    private void save()
    {
        try
        {
            ConfigurationManager.getInstance()
                    .store( configurationTree.getConfigurationNodeRoot() );
            
            Prompts.info( "Salvo com sucesso!" );
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    /**
     * initComponents
     * 
     */
    @Override
    protected void initComponents()
    {
        appendChild( configurationTree );
    }
    
    private ConfigurationTree configurationTree = new ConfigurationTree();

        
    private Action saveAction = new Action( "core/tb_save.png", "Salvar", "Salvar configurações" )
    {
        @Override
        public void onEvent( Event t ) throws Exception
        {
            save();
        }
    };
}
