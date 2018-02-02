
package com.mn.engcivil.interfaces.selectors;

import com.mn.engcivil.domain.City;
import com.mn.engcivil.interfaces.Callback;
import com.mn.engcivil.interfaces.pickers.CityPicker;

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
