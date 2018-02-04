package com.me.eng.ui.selectors;

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
