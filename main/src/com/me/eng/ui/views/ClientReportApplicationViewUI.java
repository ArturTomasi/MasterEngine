/* 
 *  Filename:    ClientReportApplicationViewUI 
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
package com.me.eng.ui.views;

import com.me.eng.services.ApplicationServices;
import com.me.eng.domain.Client;
import com.me.eng.ui.Callback;
import com.me.eng.ui.apps.Action;
import com.me.eng.ui.editors.ClientEditor;
import com.me.eng.ui.tables.ClientTable;
import com.me.eng.ui.util.Prompts;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Matheus
 */
public class ClientReportApplicationViewUI
    extends 
        ApplicationViewUI
{
    public ClientReportApplicationViewUI()
    {
        setLabel( "Clientes" );
        setIcon( "sb_client.png" );
        
        addAction( "Clientes", addAction, editAction, deleteAction );
    }
    
    @Override
    public void refreshContent()
    {
        try
        {
            clientTable.setElements( ApplicationServices.getCurrent()
                                            .getClientRepository()
                                            .findAll() );
        }
        
        catch ( Exception ex )
        {
            handleException( ex );
        }
    }
    
    private void addClient()
    {
        ClientEditor.edit( this, new Callback<Client>( new Client() )
        {
            @Override
            public void acceptInput()
            {
                try
                {
                    ApplicationServices.getCurrent()
                            .getClientRepository()
                            .add( getSource() );
                    
                    clientTable.addElement( getSource() );
                }
                
                catch ( Exception ex )
                {
                    handleException( ex );
                }
            }
        } );
    }
    
    private void editClient()
    {
        Client client = getSelectedClient();
        
        if ( client != null )
        {
            ClientEditor.edit( this, new Callback<Client>( client )
            {
                @Override
                public void acceptInput()
                {
                    try
                    {
                        ApplicationServices.getCurrent()
                                .getClientRepository()
                                .update( getSource() );

                        clientTable.updateElement( getSource() );
                    }

                    catch ( Exception ex )
                    {
                        handleException( ex );
                    }
                }
            } );
        }
    }
    
    private void deleteClient()
    {
        Client client = getSelectedClient();
        
        if ( client != null )
        {
            int samples = ApplicationServices.getCurrent()
                                .getClientRepository()
                                .countSamples( client );
            
            if ( samples > 0 )
            {
                Messagebox.show( "Este cliente possui " + samples + " amostra(s) registradas.\n" + 
                                 "Não é possível proceder com esta exclusão." );
                
                return;
            }
            
            Prompts.confirm( "Realmente excluir \"" + client + "\"?", new Callback<Client>( client )
            {
                @Override
                public void acceptInput()
                {
                    try
                    {
                        ApplicationServices.getCurrent()
                                .getClientRepository()
                                .delete( getSource() );

                        clientTable.removeElement( getSource() );
                    }

                    catch ( Exception ex )
                    {
                        handleException( ex );
                    }
                }
            } );
        }
    }
    
    private Client getSelectedClient()
    {
        Client client = clientTable.getSelectedElement();
        
        if ( client == null )
        {
            Messagebox.show( "Selecione um cliente!" );
        }
        
        return client;
    }
    
    @Override
    protected void initComponents()
    {
        setVflex( "true" );
        setHflex( "true" );
        
        clientTable.addContextAction( addAction );
        clientTable.addContextAction( editAction );
        clientTable.addContextAction( deleteAction );
        
        appendChild( clientTable );
    }
    
    private ClientTable clientTable = new ClientTable();
    
    private Action addAction = new Action( "", "Adicionar", "Adicionar cliente" )
    {
        
        @Override
        public void onEvent( Event t ) throws Exception
        {
            addClient();
        }
    };
    
    private Action editAction = new Action( "", "Editar", "Editar cliente selecionado" )
    {
        
        @Override
        public void onEvent( Event t ) throws Exception
        {
            editClient();
        }
    };
    
    private Action deleteAction = new Action( "", "Excluir", "Excluir cliente selecionado" )
    {
        
        @Override
        public void onEvent( Event t ) throws Exception
        {
            deleteClient();
        }
    };
}
