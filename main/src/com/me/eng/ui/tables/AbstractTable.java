package com.me.eng.ui.tables;

import com.me.eng.application.ApplicationContext;
import com.me.eng.ui.apps.Action;
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
        
        public void setItems( List<T> items )
        {
            this.items = items;
            
            fireEvent( ListDataEvent.CONTENTS_CHANGED, 0, -1 );
        }

        public List<T> getItems()
        {
            return items;
        }
        
        public void addItem( T e, int index )
        {
            this.items.add( index, e );
            
            fireEvent( ListDataEvent.INTERVAL_ADDED, index, index );
        }
        
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
        
        public void removeItem( T e )
        {
            int index = this.items.indexOf( e );
            
            if ( index != -1 )
            {
                this.items.remove( index );
                
                fireEvent( ListDataEvent.INTERVAL_REMOVED, index, index );
            }
        }
        
        @Override
        public T getElementAt( int i )
        {
            return items.get( i );
        }

        @Override
        public int getSize()
        {
            return items.size();
        }
    };
    
    private Model model = new Model();
    
    private TableCellRenderer tableRenderer;
    
    private List<Column> columns = new LinkedList<Column>();
    
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

    public void addColumn( Column<T> column )
    {
        addColumn( columns.size(), column );
    }
    
    public void addColumn( int index, Column<T> column )
    {
        columns.add( index, column );
        refreshColumns();
    }
    
    public void removeColumn( Column column )
    {
        columns.remove( column );
        refreshColumns();
    }

    public void setTableCellRenderer( TableCellRenderer tableRenderer )
    {
        this.tableRenderer = tableRenderer;
    }
    
    public void setElements( List<T> items )
    {
        model.setItems( items != null ? new ArrayList<T>( items ) : new LinkedList<T>() );
    }
    
    public List<T> getElements()
    {
        return new ArrayList<T>( model.getItems() );
    }
    
    public void addElement( T item )
    {
        addElement( item, model.getItems().size() );
    }
    
    public void addElement( T item, int index )
    {
        model.addItem( item, index );
    }
    
    public void updateElement( T item )
    {
        model.updateItem( item );
    }
    
    public void removeElement( T item )
    {
        model.removeItem( item );
    }
    
    public int indexOf( T item )
    {
        return model.getItems().indexOf( item );
    }
    
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
    
    public List<T> getSelectedElements()
    {
        return new ArrayList<T>( model.getSelection() );
    }
    
    public T getSelectedElement()
    {
        Listitem item = getSelectedItem();
        
        if ( item != null )
        {
            return item.getValue();
        }
        
        return  null;
    }
    
    public void addContextAction( Action a )
    {
        if ( menupopup == null )
        {
            menupopup = new Menupopup();
            
            setContext( menupopup );
            
            Executions.getCurrent().getDesktop().getFirstPage().getFirstRoot().appendChild( menupopup );
        }

        Menuitem menuitem = new Menuitem( a.getLabel() );
        menuitem.setTooltiptext( a.getTooltipText() );
        menuitem.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, a );
        
        menupopup.appendChild( menuitem );
    }
    
    protected void handleException( Exception e )
    {
        ApplicationContext.getInstance().handleException( e );
    }
    
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
    
    protected abstract Column[] getColumns();
}
