/* 
 *  Filename:    AbstractComboboxSelector 
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
package com.me.eng.core.ui.selectors;

import java.util.List;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

/**
 *
 * @author Matheus
 */
public abstract class AbstractComboboxSelector<T>
    extends 
        Combobox
{
    public AbstractComboboxSelector()
    {
        refreshContent();
    }
    
    public void refreshContent()
    {
        getChildren().clear();
        
        loadItems();
    }
    
    public void setSelectedValue( T value )
    {
        for ( Comboitem item : getItems() )
        {
            if ( item.getValue().equals( value ) )
            {
                setSelectedItem( item );
                break;
            }
        }
    }
    
    public T getSelectedValue()
    {
        if ( getSelectedItem() != null )
        {
            return getSelectedItem().getValue();
        }
        
        return null;
    }
    
    private void loadItems()
    {
        List<T> items = getElements();

        if ( items != null )
        {
            for ( T value : items )
            {
                appendItem( value.toString() ).setValue( value );
            }
        }
    }
    
    public abstract List<T> getElements();
}
