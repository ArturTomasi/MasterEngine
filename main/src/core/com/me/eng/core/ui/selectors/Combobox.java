/*
 *  Filename:    Combobox
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
package com.me.eng.core.ui.selectors;

import java.util.List;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelList;

/**
 *
 * @author Artur Tomasi
 * @param <T>
 */
public class Combobox<T>
    extends 
        org.zkoss.zul.Combobox
{
    /**
     * Combobox
     * 
     */
    public Combobox() 
    {
        setAutocomplete( false );
        setAutodrop( false );
        setReadonly( true );
        setButtonVisible( true );
        setMold( "rounded" );
        
        setWidgetListener( "onAfterSize", "$( this.$n() ).find( 'input' ).attr( 'disabled', 'true' );" );
        
    }

    /**
     * getSelectedElement
     * 
     * @return T
     */
    public T getSelectedElement()
    {
        Comboitem item = getSelectedItem();
        
        if ( item != null )
        {
            return item.getValue();
        }
        
        return null;
    }

    /**
     * setSelectedElement
     * 
     * @param element T
     */
    public void setSelectedElement( T element ) 
    {
        if ( element != null )
        {
            AbstractListModel model = (AbstractListModel) getModel();
                    
            for ( int i = 0; i < model.getSize(); i++ )
            {
                if ( model.getElementAt( i ).equals( element ) )
                {
                    model.addToSelection( element );
                }
            }
        }
        
        else
        {
            setSelectedItem( null );
        }
    }
    
    /**
     * setElements
     * 
     * @param elements List&lt;T&gt;
     */
    public void setElements( List<T> elements )
    {
        setModel( new ListModelList( elements ) );
    }
    
    /**
     * setElements
     * 
     * @param elements T...
     */
    public void setElements( T... elements )
    {
        setModel( new ListModelList( elements ) );
    }
}
