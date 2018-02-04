package com.me.eng.ui.pickers;

import com.me.eng.ui.DefaultInputPane;
import com.me.eng.ui.editors.Errors;

/**
 *
 * @author Matheus
 */
public abstract class PickerPanel<T>
    extends 
        DefaultInputPane
{
    public PickerPanel()
    {
        setTitle( "Seleção de Item" );
        setCaption( "Selecione um item" );
        setInfo( "Selecione um item da lista abaixo" );
    }

    @Override
    public void validateInput( Errors e )
    {
        if ( getSelectedItem() == null )
        {
            e.addError( "Selecione um item!" );
        }
    }
    
    public abstract void setSelectedItem( T source );
    
    public abstract T getSelectedItem();
}
