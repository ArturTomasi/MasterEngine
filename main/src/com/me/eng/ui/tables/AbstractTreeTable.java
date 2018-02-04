package com.me.eng.ui.tables;

import java.util.Collections;
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
        public Model( DefaultTreeNode root )
        {
            super( root );
        }
        
        @Override
        public boolean isLeaf( DefaultTreeNode e )
        {
            return e.isLeaf();
        }

        @Override
        public DefaultTreeNode getChild( DefaultTreeNode e, int i )
        {
            return (DefaultTreeNode) e.getChildAt( i );
        }

        @Override
        public int getChildCount( DefaultTreeNode e )
        {
            return e.getChildCount();
        }
    }

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
    
    public void setRootNode( DefaultTreeNode<T> node )
    {
        setModel( new Model( node ) );
    }
    
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
    
    protected abstract Column[] getColumns();
    protected abstract Object getValueAt( Column c, T value );
}
