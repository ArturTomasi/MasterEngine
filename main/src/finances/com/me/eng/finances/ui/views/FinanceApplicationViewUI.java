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

import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.apps.Action;
import com.me.eng.core.ui.util.Prompts;
import com.me.eng.core.ui.views.ApplicationViewUI;
import com.me.eng.finances.controllers.PostingController;
import com.me.eng.finances.domain.Posting;
import com.me.eng.finances.domain.PostingFilter;
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
    
    private PostingFilter filter = PostingFilter.PENDENCY;
    
    /**
     * FinanceApplicationViewUI
     * 
     */
    public FinanceApplicationViewUI() 
    {
        setLabel( "Finanças" );
        setIcon( "finances/sb_finance.png" );
        addAction( "Lançamentos", addAction, editAction, deleteAction );
        addAction( "Movimentações", finishAction, copyAction, reverseAction );
        addAction( "Filtro", filterAllAction, filterPendencyAction );
        addAction( "Relatórios" );
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
            list.setElements( filter.getElements() );
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
     * reverse
     * 
     */
    private void reverse()
    {
        Posting posting = list.getSelectedElement();
        
        String validate = controller.validateReserve( posting );
        
        if ( validate == null )
        {
            controller.reversePosting( posting );

            refreshContent();
            
            Prompts.info( "Lançamento revertido com sucesso!" );
        }
        
        else
        {
            Prompts.alert( validate );
        }
    }
    
    
    /**
     * copy
     * 
     */
    private void copy()
    {
        Posting posting =  list.getSelectedElement();
        
        if( posting == null )
        {
            Prompts.alert( "Selecione um Lançamento para copiar!" );
            
            return;
        }
        
        Prompts.confirm( "Confirmar Cópia", "Você tem certeza que deseja copiar o Lançamento: \"" + posting.getName() + "\"", new Callback()
        {
            @Override
            public void acceptInput() throws Exception 
            {
                PostingEditor.edit( FinanceApplicationViewUI.this, PostingEditor.Mode.NEW, new Callback<Posting>( posting.clone() )
                {
                    @Override
                    public void acceptInput() throws Exception 
                    {
                        controller.addPosting( getSource() );

                        refreshContent();

                        Prompts.info( "Adicionado com sucesso!" );
                    }
                } );
            }
        } );
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
    
    private Action reverseAction = new Action( "finances/tb_reverse.png", "Retornar", "Retornar lançamento selecionado!" ) 
    {
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            reverse();
        }
    };
    
    private Action copyAction = new Action( "core/tb_copy.png", "Copiar", "Copiar lançamento selecionado!" ) 
    {
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            copy();
        }
    };
    
    private Action filterAllAction = new Action( "core/tb_filter.png", "Todos", "Buscar todos os lançamentos!" ) 
    {
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            filter = PostingFilter.ALL;
            
            refreshContent();
        }
    };
    
    private Action filterPendencyAction = new Action( "core/tb_filter.png", "Pendentes", "Buscar lançamentos pendentes!" ) 
    {
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            filter = PostingFilter.PENDENCY;
            
            refreshContent();
        }
    };
}
