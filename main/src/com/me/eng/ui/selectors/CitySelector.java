
package com.me.eng.ui.selectors;

import com.me.eng.domain.City;
import com.me.eng.ui.Callback;
import com.me.eng.ui.pickers.CityPicker;

/**
 *
 * @author Matheus
 */
public class CitySelector
    extends 
        AbstractFieldSelector<City>
{
    @Override
    protected void chooseItem( Callback callback )
    {
        CityPicker.pick( this, callback );
    }
}
