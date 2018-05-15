/*
 *  Filename:    CategoryApplicationView
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
import com.me.eng.finances.domain.PostingCategory;
import com.me.eng.finances.ui.editors.PostingCategoryEditor;
import com.me.eng.finances.ui.lists.PostingCategoryList;
import org.zkoss.zk.ui.event.Event;

/**
 *
 * @author Artur Tomasi
 */
public class PostingCategoryApplicationViewUI 
    extends 
        ApplicationViewUI
{
    
    /**
     * CategoryApplicationViewUI
     * 
     */
    public PostingCategoryApplicationViewUI()
    {
        setLabel( "Categorias" );
        setIcon( "finances/sb_categories.png" );
        
        addAction( "Categorias", addAction, editAction, deleteAction );
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
            table.setElements( ApplicationServices.getCurrent().getPostingCategoryRepository().findAll() );
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
        PostingCategoryEditor.edit( this, new Callback<PostingCategory>( new PostingCategory() )
        {
            @Override
            public void acceptInput() throws Exception 
            {
                ApplicationServices.getCurrent().getPostingCategoryRepository().add( getSource() );
                
                table.addElement( getSource() );
            }
        } );
    }

    /**
     * edit
     * 
     */
    private void edit()
    {
        PostingCategory category = table.getSelectedElement();
        
        if ( category != null )
        {
            PostingCategoryEditor.edit( this, new Callback<PostingCategory>( category )
            {
                @Override
                public void acceptInput() throws Exception 
                {
                    ApplicationServices.getCurrent().getPostingCategoryRepository().update( getSource() );
                    
                    table.updateElement( getSource() );
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
        PostingCategory category = table.getSelectedElement();
        
        if ( category != null )
        {
            Prompts.confirm( "Deseja realmente excluir categoria?", new Callback<PostingCategory>( category )
            {
                @Override
                public void acceptInput() throws Exception
                {
                    ApplicationServices.getCurrent().getPostingCategoryRepository().delete( getSource() );
                    
                    table.removeElement( getSource() );
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
        
        table.addContextAction( editAction );
        table.addContextAction( deleteAction );
        
        table.setVflex( "true" );
        table.setHflex( "true" );
                
        appendChild( table );
    }
    
    private PostingCategoryList table = new PostingCategoryList();
    
    private Action addAction = new Action( "core/tb_add.png", "Novo", "Nova categoria de lançamento!" ) 
    {
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            add();
        }
    };
    
    private Action editAction = new Action( "core/tb_edit.png", "Editar", "Editar categoria de lançamento!" ) 
    {
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            edit();
        }
    };
    
    private Action deleteAction = new Action( "core/tb_delete.png", "Excluir", "Excluir categoria de lançamento!" ) 
    {
        @Override
        public void onEvent( Event t ) throws Exception 
        {
            delete();
        }
    };
}
