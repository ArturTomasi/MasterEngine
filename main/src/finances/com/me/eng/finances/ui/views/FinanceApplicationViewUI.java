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
import com.me.eng.finances.domain.Posting;
import com.me.eng.finances.ui.editors.PostingEditor;
import org.zkoss.zk.ui.event.Event;

/**
 *
 * @author Artur Tomasi
 */
public class FinanceApplicationViewUI 
    extends 
        ApplicationViewUI
{

    /**
     * FinanceApplicationViewUI
     * 
     */
    public FinanceApplicationViewUI() 
    {
        setLabel( "Finanças" );
        setIcon( "finances/sb_finance.png" );
        addAction( "Lançamentos", addAction, editAction );
    }
    
    /**
     * add
     * 
     */
    private void add()
    {
        PostingEditor.edit( this, new Callback<Posting>( new Posting() )
        {
            @Override
            public void acceptInput() throws Exception 
            {
                System.out.println( getSource() );
            }
            
        } );
    }
    
    /**
     * edit
     * 
     */
    private void edit()
    {
        Prompts.info( "Lançamento inserido com sucesso!" );
    }
    
    /**
     * initComponents
     * 
     */
    @Override
    protected void initComponents() 
    {
    }
    
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
}
