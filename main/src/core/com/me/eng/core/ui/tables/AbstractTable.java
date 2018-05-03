/* 
 *  Filename:    AbstractTable 
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
package com.me.eng.core.ui.tables;

import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.ui.apps.Action;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.event.ListDataEvent;

/**
 *
 * @author Matheus
 * @param <T>
 */
public abstract class AbstractTable<T>
    extends 
        Listbox
{
    private class Model
        extends 
            AbstractListModel<T>
    {
        private List<T> items = Collections.emptyList();
        
        /**
         * setItems
         * 
         * @param items List&lt;T&gt;
         */
        public void setItems( List<T> items )
        {
            this.items = items;
            
            fireEvent( ListDataEvent.CONTENTS_CHANGED, 0, -1 );
        }

        /**
         * getItems
         * 
         * @return List&lt;T&gt;
         */
        public List<T> getItems()
        {
            return items;
        }
        
        /**
         * addItem
         * 
         * @param e T
         * @param index int
         */
        public void addItem( T e, int index )
        {
            this.items.add( index, e );
            
            fireEvent( ListDataEvent.INTERVAL_ADDED, index, index );
        }
        
        /**
         * updateItem
         * 
         * @param e T
         */
        public void updateItem( T e )
        {
            int index = this.items.indexOf( e );
            
            if ( index != -1 )
            {
                if ( this.items.remove( e ) )
                {
                    this.items.add( index, e );
                    
                    fireEvent( ListDataEvent.CONTENTS_CHANGED, index, index );
                }
            }
        }
        
        /**
         * removeItem
         * 
         * @param e T
         */
        public void removeItem( T e )
        {
            int index = this.items.indexOf( e );
            
            if ( index != -1 )
            {
                this.items.remove( index );
                
                fireEvent( ListDataEvent.INTERVAL_REMOVED, index, index );
            }
        }
        
        /**
         * getElementAt
         * 
         * @param i int
         * @return T
         */
        @Override
        public T getElementAt( int i )
        {
            return items.get( i );
        }

        /**
         * getSize
         * 
         * @return int
         */
        @Override
        public int getSize()
        {
            return items.size();
        }
    };
    
    private Model model = new Model();
    
    private TableCellRenderer tableRenderer;
    
    private List<Column> columns = new LinkedList();
    
    /**
     * AbstractTable
     * 
     */
    public AbstractTable()
    {
        setModel( model );
        
        setSizedByContent( true );
        setHflex( "true" );
        setVflex( "true" );
        setEmptyMessage( "Sem registros" );
        setMold( "paging" );
        setPageSize( 50 );
        
        setItemRenderer( new ListitemRenderer<T>()
        {
            @Override
            public void render( Listitem lstm, T t, int i ) throws Exception
            {
                lstm.setValue( t );
                
                for ( Column c : columns )
                {
                    Listcell cell = new Listcell();
                    
                    Object v = c.getValueAt( t );
                    
                    if ( v instanceof Component )
                    {
                        cell.appendChild( (Component) v);
                    }
                    
                    else 
                    {
                        cell.setLabel( Objects.isNull( v ) ? "n/d" : v.toString() );
                    }
                    
                    if ( tableRenderer != null )
                    {
                        tableRenderer.render( t, c, cell );
                    }

                    lstm.appendChild( cell );
                }
            }
        } );
        
        refreshColumns();
    }

    /**
     * addColumn
     * 
     * @param column Column&lt;T&gt;
     */
    public void addColumn( Column<T> column )
    {
        addColumn( columns.size(), column );
    }
    
    /**
     * addColumn
     * 
     * @param index int
     * @param column Column&lt;T&gt;
     */
    public void addColumn( int index, Column<T> column )
    {
        columns.add( index, column );
        refreshColumns();
    }
    
    /**
     * removeColumn
     * 
     * @param column Column
     */
    public void removeColumn( Column column )
    {
        columns.remove( column );
        refreshColumns();
    }

    /**
     * setTableCellRenderer
     * 
     * @param tableRenderer TableCellRenderer
     */
    public void setTableCellRenderer( TableCellRenderer tableRenderer )
    {
        this.tableRenderer = tableRenderer;
    }
    
    /**
     * setElements
     * 
     * @param items List&lt;T&gt;
     */
    public void setElements( List<T> items )
    {
        model.setItems( items != null ? new ArrayList<T>( items ) : new LinkedList<T>() );
    }
    
    /**
     * getElements
     * 
     * @return List&lt;T&gt;
     */
    public List<T> getElements()
    {
        return new ArrayList<T>( model.getItems() );
    }
    
    /**
     * addElement
     * 
     * @param item T
     */
    public void addElement( T item )
    {
        addElement( item, model.getItems().size() );
    }
    
    /**
     * addElement
     * 
     * @param item T
     * @param index int
     */
    public void addElement( T item, int index )
    {
        model.addItem( item, index );
    }
    
    /**
     * updateElement
     * 
     * @param item T
     */
    public void updateElement( T item )
    {
        model.updateItem( item );
    }
    
    /**
     * removeElement
     * 
     * @param item T
     */
    public void removeElement( T item )
    {
        model.removeItem( item );
    }
    
    /**
     * indexOf
     * 
     * @param item T
     * @return int
     */
    public int indexOf( T item )
    {
        return model.getItems().indexOf( item );
    }
    
    /**
     * setSelectedElement
     * 
     * @param item T
     */
    public void setSelectedElement( T item )
    {
        clearSelection();
        
        if ( item != null )
        {
            model.addToSelection( item );
            
            int index = model.getItems().indexOf( item );
            
            if ( index != -1 )
            {
                int page = index / getPageSize();
                
                if ( page != getActivePage() )
                {
                    setActivePage( (int) page );
                }
            }
        }
    }
    
    /**
     * setSelectedElements
     * 
     * @param items List&lt;T&gt;
     */
    public void setSelectedElements( List<T> items )
    {
        clearSelection();
        
        if ( items != null )
        {
            for ( T item : items )
            {
                model.addToSelection( item );
            }
        }
    }
    
    /**
     * getSelectedElements
     * 
     * @return List&lt;T&gt;
     */
    public List<T> getSelectedElements()
    {
        return new ArrayList<T>( model.getSelection() );
    }
    
    /**
     * getSelectedElement
     * 
     * @return T
     */
    public T getSelectedElement()
    {
        Listitem item = getSelectedItem();
        
        if ( item != null )
        {
            return item.getValue();
        }
        
        return  null;
    }
    
    /**
     * addContextAction
     * 
     * @param a Action
     */
    public void addContextAction( Action a )
    {
        if ( menupopup == null )
        {
            menupopup = new Menupopup();
            
            setContext( menupopup );
            
            Executions.getCurrent().getDesktop().getFirstPage().getFirstRoot().appendChild( menupopup );
        }

        Menuitem menuitem = new Menuitem( a.getLabel() );
        menuitem.setImage( a.getIcon() );
        menuitem.setTooltiptext( a.getTooltipText() );
        menuitem.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, a );
        
        menupopup.appendChild( menuitem );
    }
    
    /**
     * handleException
     * 
     * @param e Exception
     */
    protected void handleException( Exception e )
    {
        ApplicationContext.getInstance().handleException( e );
    }
    
    /**
     * refreshColumns
     * 
     */
    private void refreshColumns()
    {
        if ( columns.isEmpty() )
        {
            columns = new ArrayList<>( Arrays.asList( getColumns() ) );
        }
        
        if ( getFellowIfAny( "head" ) != null )
        {
            getFellow( "head" ).detach();
        }
        
        Listhead head = new Listhead();
        head.setId( "head" );
        head.setSizable( true );
        head.setMenupopup( "auto" );
        
        for ( Column col : columns )
        {
            Listheader header = new Listheader( col.getLabel() );
//            header.setSort( "auto" );

            if ( col.getWidth().endsWith( "px" ) || col.getWidth().endsWith( "%" ) )
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

    private Menupopup menupopup;
    
    /**
     * Column[]
     * 
     * @return abstract
     * @ignored getColumns
     */
    protected abstract Column[] getColumns();
}
