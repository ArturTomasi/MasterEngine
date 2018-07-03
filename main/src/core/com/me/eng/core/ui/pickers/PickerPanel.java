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
package com.me.eng.core.ui.pickers;

import com.me.eng.core.ui.DefaultInputPane;
import com.me.eng.core.ui.editors.Errors;
import com.me.eng.core.ui.util.GenericObserver;

/**
 *
 * @author Matheus
 */
public abstract class PickerPanel<T>
    extends 
        DefaultInputPane
{
    private GenericObserver<T> closeListener;
    
    /**
     * PickerPanel
     * 
     */
    public PickerPanel()
    {
        setTitle( "Selecione um item" );
        setInfo( "Selecione um item da lista abaixo" );
    }
    
    /**
     * validateInput
     * 
     * @param e Errors
     */
    @Override
    public void validateInput( Errors e )
    {
        if ( getSelectedItem() == null )
        {
            e.addError( "Selecione um item!" );
        }
    }
    
    /**
     * void
     * 
     * @param source T
     * @return abstract
     * @ignored setSelectedItem
     */
    public abstract void setSelectedItem( T source );
    
    /**
     * T
     * 
     * @return abstract
     * @ignored getSelectedItem
     */
    public abstract T getSelectedItem();

    /**
     * onClose
     * 
     * @param observer GenericObserver&lt;T&gt;
     */
    public void onClose( GenericObserver<T> observer )
    {
        closeListener = observer;
    }
    
    /**
     * close
     * 
     */
    public void close()
    {
        if ( closeListener != null )
        {
            closeListener.notify( getSelectedItem() );
        }
    }
}
