/*
 * Filename:    SectorApplicationViewUI 
 *
 * Author:      Artur Tomasi
 * EMail:       tomasi.artur@gmail.com
 * Internet:    https://www.masterengine.com.br
 *
 * Copyright © 2019 by Over Line Ltda.
 * 95900-038, LAJEADO, RS
 * BRAZIL
 *
 * The copyright to the computer program(s) herein
 * is the property of Over Line Ltda., Brazil.
 * The program(s) may be used and/or copied only with
 * the written permission of Over Line Ltda.
 * or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which
 * the program(s) have been supplied.
 */
package com.me.eng.core.ui.views;

import com.me.eng.core.domain.Sector;
import com.me.eng.core.services.ApplicationServices;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.apps.Action;
import com.me.eng.core.ui.editors.SectorEditor;
import com.me.eng.core.ui.tables.SectorTable;
import com.me.eng.core.ui.util.Prompts;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Vlayout;

/**
 *
 * @author Artur Tomasi
 */
public class SectorApplicationViewUI
    extends 
        ApplicationViewUI
{
    /**
     * SectorApplicationViewUI
     * 
     */
    public SectorApplicationViewUI()
    {
        setIcon( "core/sb_sector.png" );
        setLabel( "Setores" );
        
        addAction( "Setores", addAction, editAction, deleteAction );
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
            sectorTable.setElements( ApplicationServices.getCurrent()
                                        .getSectorRepository()
                                        .findAll() );   
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    /**
     * getSelectedSector
     * 
     * @return Sector
     */
    private Sector getSelectedSector()
    {
        Sector sector = sectorTable.getSelectedElement();
        
        if ( sector == null )
        {
            Prompts.alert( "Selecione um setor!" );
        }
        
        return sector;
    }
    
    /**
     * addUser
     * 
     */
    private void addSetor()
    {
        SectorEditor.edit( this, new Callback<Sector>( new Sector() )
        {
            @Override
            public void acceptInput() throws Exception
            {
                ApplicationServices.getCurrent()
                        .getSectorRepository()
                        .add( getSource() );

                sectorTable.addElement( getSource() );
                sectorTable.setSelectedElement( getSource() );
            }
        } );
    }
    
    /**
     * editSetor
     * 
     */
    private void editSetor()
    {
        Sector sector = getSelectedSector();
        
        if ( sector != null )
        {
            SectorEditor.edit( this, new Callback<Sector>( sector )
            {
                @Override
                public void acceptInput() throws Exception
                {
                    ApplicationServices.getCurrent()
                            .getSectorRepository()
                            .update( getSource() );

                    sectorTable.updateElement( getSource() );
                }
            } );
        }
    }
    
    /**
     * deleteSetor
     * 
     */
    private void deleteSetor()
    {
        Sector sector = getSelectedSector();
        
        if ( sector != null )
        {
//            if ( sector.equals( ApplicationContext.getInstance().getActiveUser() ) )
//            {
//                Prompts.alert( "Não é possível excluir o usuário ativo!" );
//                return;
//            }
//            
//            if ( sector.isSystemUser() )
//            {
//                Prompts.alert( "Este é o usuário Administrador e não pode ser removido!" );
//                return;
//            }
            
            Prompts.confirm( "Realmente excluir \"" + sector + "\"?", new Callback<Sector>( sector )
            {
                @Override
                public void acceptInput() throws Exception
                {
                    ApplicationServices.getCurrent()
                            .getSectorRepository()
                            .delete( sector );

                    sectorTable.removeElement( sector );
                }
            } );
        }
    }
    
    /**
     * initComponents
     * 
     */
    @Override
    protected void initComponents()
    {
        setVflex( "true" );
        setHflex( "true" );
        
        Vlayout vlayout = new Vlayout();
        vlayout.setVflex( "true" ); 
        vlayout.setHflex( "true" ); 
        
        sectorTable = new SectorTable();
        sectorTable.addContextAction( editAction );
        sectorTable.addContextAction( deleteAction );
        
        vlayout.appendChild( sectorTable );
        
        appendChild( vlayout );
    }
    
    private SectorTable sectorTable;
    
    private Action addAction = new Action( "core/tb_add.png", "Novo", "Novo setor" )
    {
        @Override
        public void onEvent( Event t ) throws Exception
        {
            addSetor();
        }
    };
    
    private Action editAction = new Action( "core/tb_edit.png", "Editar", "Editar setor selecionad" )
    {
        @Override
        public void onEvent( Event t ) throws Exception
        {
            editSetor();
        }
    };
    
    private Action deleteAction = new Action( "core/tb_delete.png", "Excluir", "Excluir setor selecionado" )
    {
        @Override
        public void onEvent( Event t ) throws Exception
        {
            deleteSetor();
        }
    };
}
