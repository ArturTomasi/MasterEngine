/*
 *  Filename:    EntityApplicationViewUI
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
 *  is the property of Over Line Ltda.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda,
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.finances.ui.views;

import com.me.eng.core.services.ApplicationServices;
import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.apps.Action;
import com.me.eng.core.ui.util.Prompts;
import com.me.eng.core.ui.views.ApplicationViewUI;
import com.me.eng.finances.domain.Company;
import com.me.eng.finances.ui.editors.CompanyEditor;
import com.me.eng.finances.ui.lists.CompanyList;
import org.zkoss.zk.ui.event.Event;

/**
 *
 * @author Artur Tomasi
 */
public class CompanyApplicationViewUI 
    extends 
        ApplicationViewUI
{
    /**
     * CompanyApplicationViewUI
     * 
     */
    public CompanyApplicationViewUI() 
    {
        setLabel( "Companhias" );
        setIcon( "finances/fi_company.png" );
        
        addAction( "Companhias", addAction, editAction, deleteAction );
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
            companyList.setElements( ApplicationServices.getCurrent().getCompanyRepository().findAll() );
        }
        
        catch ( Exception e )
        {
            handleException( e );
        }
    }
    
    /**
     * add
     * 
     */
    private void add()
    {
        CompanyEditor.edit( this, new Callback<Company>( new Company() )
        {
            @Override
            public void acceptInput() throws Exception 
            {
                ApplicationServices.getCurrent().getCompanyRepository().add( getSource() );
                
                companyList.addElement( getSource() );
                
                Prompts.info( "Inserido com sucesso!" );
            }
        } );
    }

    /**
     * edit
     * 
     */
    private void edit()
    {
        Company company = companyList.getSelectedElement();
        
        if ( company != null )
        {
            CompanyEditor.edit( this, new Callback<Company>( company )
            {
                @Override
                public void acceptInput() throws Exception 
                {
                    ApplicationServices.getCurrent().getCompanyRepository().update( getSource() );
                    
                    companyList.updateElement( getSource() );
                    
                    Prompts.info( "Atualizado com sucesso!" );
                }
            } );
        }
    }
    
    /**
     * delete
     * 
     */
    private void delete()
    {
        Company company = companyList.getSelectedElement();
        
        if ( company != null )
        {
            Prompts.confirm( "Deseja realmente excluir a companhia?", new Callback<Company>( company )
            {
                @Override
                public void acceptInput() throws Exception
                {
                    ApplicationServices.getCurrent().getCompanyRepository().delete( getSource() );
                    
                    companyList.removeElement( getSource() );
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
        
        companyList.addContextAction( editAction );
        companyList.addContextAction( deleteAction );
        
        companyList.setVflex( "true" );
        companyList.setHflex( "true" );
                
        appendChild( companyList );
    }
    
    private CompanyList companyList = new CompanyList();
    
      private Action addAction = new Action( "core/tb_add.png", "Novo", "Nova companhia de lançamento!" ) 
    {
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            add();
        }
    };
    
    private Action editAction = new Action( "core/tb_edit.png", "Editar", "Editar companhia de lançamento!" ) 
    {
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            edit();
        }
    };
    
    private Action deleteAction = new Action( "core/tb_delete.png", "Excluir", "Excluir companhia de lançamento!" ) 
    {
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            delete();
        }
    };
}
