/* 
 *  Filename:    PickerPanel 
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
