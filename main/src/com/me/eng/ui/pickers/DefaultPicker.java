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
package com.me.eng.ui.pickers;

import com.me.eng.ui.Callback;
import com.me.eng.ui.DefaulInputWindow;
import org.zkoss.zk.ui.Component;

/**
 *
 * @author Matheus
 */
public class DefaultPicker<V>
    extends 
        DefaulInputWindow<PickerPanel, V>
{
    public static DefaultPicker createPicker( Component owner, PickerPanel pickerPanel, Callback callback )
    {
        pickerPanel.setSelectedItem( callback.getSource() );
        
        return createInputWindow( owner, DefaultPicker.class, pickerPanel, callback );
    }

    @Override
    protected void getInput( V source )
    {
        callback.setSource( (V) defaultPane.getSelectedItem());
    }
}
