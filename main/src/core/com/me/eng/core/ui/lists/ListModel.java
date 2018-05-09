/*
 *  Filename:    ListModel
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

import java.util.Collections;
import java.util.List;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.event.ListDataEvent;

/**
 *
 * @author Artur Tomasi
 */
public class ListModel<T>
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
