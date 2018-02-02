package com.mn.engcivil.interfaces.pickers;

import com.mn.engcivil.interfaces.DefaultInputPane;
import com.mn.engcivil.interfaces.editors.Errors;

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
