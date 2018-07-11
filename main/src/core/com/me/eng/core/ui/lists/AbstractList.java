/*
 *  Filename:    AbstractList
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
package com.me.eng.core.ui.lists;

import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.ui.apps.Action;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Html;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;

/**
 *
 * @author Artur Tomasi
 * @param <T>
 */
public abstract class AbstractList<T>
    extends 
        Listbox
{
    private Menupopup menupopup;
    
    private ListModel model = new ListModel();
    
    /**
     * AbstractTable
     * 
     */
    public AbstractList()
    {
        setModel( model );
        
        setHflex( "true" );
        setVflex( "true" );
        setEmptyMessage( "Sem registros" );
        setMold( "paging" );
        setPageSize( 50 );
        
        setItemRenderer( ( Listitem lstm, T t, int i ) ->  
        {
            lstm.setValue( t );
            
            Listcell cell = new Listcell();
            cell.appendChild( new Html( doContent( t ) ) );
            
            cell.setParent( lstm );
        } );
    }

    /**
     * setElements
     * 
     * @param items List&lt;T&gt;
     */
    public void setElements( List<T> items )
    {
        model.setItems( items );
    }
    
    /**
     * getElements
     * 
     * @return List&lt;T&gt;
     */
    public List<T> getElements()
    {
        return model.getItems();
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
            items.forEach( model::addToSelection );
        }
    }
    
    /**
     * getSelectedElements
     * 
     * @return List&lt;T&gt;
     */
    public List<T> getSelectedElements()
    {
        return new ArrayList( model.getSelection() );
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
        
        if ( a.getUpload() == null )
        {
            menuitem.addEventListener( org.zkoss.zk.ui.event.Events.ON_CLICK, a );
        }

        else
        {
            menuitem.setUpload( a.getUpload() );
            menuitem.addEventListener( 1000, org.zkoss.zk.ui.event.Events.ON_UPLOAD, e -> menupopup.close() );
            menuitem.addEventListener( org.zkoss.zk.ui.event.Events.ON_UPLOAD, a );
        }
        
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
     * String
     * 
     * @param source T
     * @return abstract
     * @ignored doContent
     */
    public abstract String doContent( T source );
}
