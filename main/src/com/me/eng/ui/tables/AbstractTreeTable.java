/* 
 *  Filename:    AbstractTreeTable 
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
package com.me.eng.ui.tables;

import java.util.Objects;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treecol;
import org.zkoss.zul.Treecols;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

/**
 *
 * @author Matheus
 */
public abstract class AbstractTreeTable<T>
    extends 
        Tree
{
    private class Model
        extends 
            AbstractTreeModel<DefaultTreeNode>
    {
        /**
         * Model
         * 
         * @param root DefaultTreeNode
         */
        public Model( DefaultTreeNode root )
        {
            super( root );
        }
        
        /**
         * isLeaf
         * 
         * @param e DefaultTreeNode
         * @return boolean
         */
        @Override
        public boolean isLeaf( DefaultTreeNode e )
        {
            return e.isLeaf();
        }

        /**
         * getChild
         * 
         * @param e DefaultTreeNode
         * @param i int
         * @return DefaultTreeNode
         */
        @Override
        public DefaultTreeNode getChild( DefaultTreeNode e, int i )
        {
            return (DefaultTreeNode) e.getChildAt( i );
        }

        /**
         * getChildCount
         * 
         * @param e DefaultTreeNode
         * @return int
         */
        @Override
        public int getChildCount( DefaultTreeNode e )
        {
            return e.getChildCount();
        }
    }

    /**
     * AbstractTreeTable
     * 
     */
    public AbstractTreeTable()
    {
        setItemRenderer( new TreeitemRenderer<T>()
        {
            @Override
            public void render( Treeitem item, T t, int i ) throws Exception
            {
                item.setValue( t );
                item.setOpen( true );
                
                Treerow row = new Treerow();
                
                for ( Column c : getColumns() )
                {
                    Treecell cell = new Treecell();
                    
                    Object v = getValueAt( c, t );
                    
                    if ( v instanceof Component )
                    {
                        cell.appendChild( (Component) v);
                    }
                    
                    else 
                    {
                        cell.setLabel( Objects.isNull( v ) ? "n/d" : v.toString() );
                    }

                    row.appendChild( cell );
                }
                
                item.appendChild( row );
            }
        } );
        
        init();
    }
    
    /**
     * setRootNode
     * 
     * @param node DefaultTreeNode&lt;T&gt;
     */
    public void setRootNode( DefaultTreeNode<T> node )
    {
        setModel( new Model( node ) );
    }
    
    /**
     * init
     * 
     */
    private void init() 
    {
        setSizedByContent( true );
        setHflex( "true" );
        setVflex( "true" );
        
        Treecols head = new Treecols();
        head.setSizable( true );
        
        for ( Column col : getColumns() )
        {
            Treecol header = new Treecol( col.getLabel() );
            
            if ( col.getWidth().matches( "[%|px]" ) )
            {
                header.setWidth( col.getWidth() );
            }
            
            else
            {
                header.setHflex( col.getWidth() );
            }
            
            head.appendChild( header );
        }
        
        appendChild( head );
    }
    
    /**
     * Column[]
     * 
     * @return abstract
     * @ignored getColumns
     */
    protected abstract Column[] getColumns();
    
    /**
     * Object
     * 
     * @param c Column
     * @param value T
     * @return abstract
     * @ignored getValueAt
     */
    protected abstract Object getValueAt( Column c, T value );
}
