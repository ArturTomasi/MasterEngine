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
