/*
 *  Filename:    FinanceApplicationView
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
import com.me.eng.finances.controllers.PostingController;
import com.me.eng.finances.domain.Posting;
import com.me.eng.finances.ui.editors.PostingEditor;
import com.me.eng.finances.ui.lists.PostingList;
import org.zkoss.zk.ui.event.Event;

/**
 *
 * @author Artur Tomasi
 */
public class FinanceApplicationViewUI 
    extends 
        ApplicationViewUI
{
    private PostingController controller = PostingController.getInstance();
    
    /**
     * FinanceApplicationViewUI
     * 
     */
    public FinanceApplicationViewUI() 
    {
        setLabel( "Finanças" );
        setIcon( "finances/sb_finance.png" );
        addAction( "Lançamentos", addAction, editAction, deleteAction );
        addAction( "Movimentações", finishAction );
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
            list.setElements( ApplicationServices.getCurrent().getPostingRepository().findAll() );
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
        PostingEditor.edit( this, PostingEditor.Mode.NEW, new Callback<Posting>( new Posting() )
        {
            @Override
            public void acceptInput() throws Exception 
            {
                controller.addPosting( getSource()  );
                
                refreshContent();
                
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
        Posting posting = list.getSelectedElement();
        
        String validate = controller.validateEdit( posting );
        
        if( validate == null )
        {
            PostingEditor.edit( this, PostingEditor.Mode.EDIT, new Callback<Posting>( posting )
            {
                @Override
                public void acceptInput() throws Exception 
                {
                    controller.editPosting( getSource() );

                    refreshContent();

                    Prompts.info( "Atualizado com sucesso!" );
                }
            } );
        }
        
        else
        {
            Prompts.alert( validate );
        }
    }
    
    /**
     * delete
     * 
     */
    private void delete()
    {
        Posting posting =  list.getSelectedElement();
        
        String validate = controller.validateDelete( posting );
        
        if( validate == null )
        {
            String message = "Você tem certeza que deseja excluir o Lançamento: " + posting.getName();
            
            if( posting.isRepeat() )
                    message += "\n Todas os lançamentos recorrentes posteriores serão excluidos também !";
            
            Prompts.confirm( "Confirmar Exclusão",  message, new Callback()
            {
                @Override
                public void acceptInput() throws Exception 
                {
                    controller.deletePosting( posting );

                    refreshContent();
                }
                
            } );
        }
        
        else
        {
            Prompts.alert( validate );
        }
    }
    
    /**
     * finish
     * 
     */
    private void finish()
    {
        Posting posting =  list.getSelectedElement();
        
        String validate = controller.validateFinish( posting );
        
        if( validate == null )
        {
            PostingEditor.edit( this, PostingEditor.Mode.FINISH, new Callback<Posting>( posting )
            {
                @Override
                public void acceptInput() throws Exception 
                {
                    controller.finishPosting( getSource() );

                    refreshContent();

                    Prompts.info( "Finalizado com sucesso!" );
                }
            } );
        }
        
        else
        {
            Prompts.alert( validate );
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
        
        list.setHflex( "true" );
        list.setVflex( "true" );
        
        list.addContextAction( finishAction );
        list.addContextAction( deleteAction );
        
        appendChild( list );
    }
    
    private PostingList list = new PostingList();
    
    private Action addAction = new Action( "core/tb_add.png", "Novo", "Adicionar novo lançamento!" ) 
    {
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            add();
        }
    };
    
    private Action editAction = new Action( "core/tb_edit.png", "Editar", "Editar lançamento selecionado!" ) 
    {
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            edit();
        }
    };
    
    private Action deleteAction = new Action( "core/tb_delete.png", "Excluir", "Excluir lançamento selecionado!" ) 
    {
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            delete();
        }
    };
    
    private Action finishAction = new Action( "finances/tb_finish.png", "Finalizar", "Finalizar lançamento selecionado!" ) 
    {
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            finish();
        }
    };
}
