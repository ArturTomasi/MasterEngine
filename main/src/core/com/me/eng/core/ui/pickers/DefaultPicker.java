/* 
 *  Filename:    DefaultPicker 
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

import com.me.eng.core.ui.Callback;
import com.me.eng.core.ui.DefaulInputWindow;
import com.me.eng.core.ui.util.GenericObserver;
import org.zkoss.zk.ui.Component;

/**
 *
 * @author Matheus
 */
public class DefaultPicker<V>
    extends 
        DefaulInputWindow<PickerPanel, V>
{
    /**
     * createPicker
     * 
     * @param owner Component
     * @param pickerPanel PickerPanel
     * @param callback Callback
     * @return DefaultPicker
     */
    public static DefaultPicker createPicker( Component owner, PickerPanel pickerPanel, Callback callback )
    {
        DefaultPicker picker = createInputWindow( owner, DefaultPicker.class, pickerPanel, callback );
        
        pickerPanel.setSelectedItem( callback.getSource() );
     
        pickerPanel.onClose( (Object s) ->  picker.acceptInput() );
        
        return picker;
    }

    /**
     * setTitle
     * 
     * @param title String
     */
    @Override
    public void setTitle( String title ) 
    {
        getDefaultPane().setTitle( title );
        
        refreshCaption();
    } 
    
    /**
     * setInfo
     * 
     * @param info String
     */
    public void setInfo( String info ) 
    {
        getDefaultPane().setInfo( info );
        
        refreshCaption();
    }
    
    /**
     * setIcon
     * 
     * @param icon String
     */
    public void setIcon( String icon ) 
    {
        getDefaultPane().setIcon( icon );
        
        refreshCaption();
    } 
    
    /**
     * getInput
     * 
     * @param source V
     */
    @Override
    protected void getInput( V source )
    {
        callback.setSource( (V) defaultPane.getSelectedItem());
    }
}
