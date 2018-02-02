package com.mn.engcivil.interfaces.pickers;

import com.mn.engcivil.interfaces.Callback;
import com.mn.engcivil.interfaces.DefaulInputWindow;
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
