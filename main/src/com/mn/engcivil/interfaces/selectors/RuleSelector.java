package com.mn.engcivil.interfaces.selectors;

import com.mn.engcivil.domain.Rule;
import com.mn.engcivil.interfaces.Callback;
import com.mn.engcivil.interfaces.pickers.RulePicker;

/**
 *
 * @author Matheus
 */
public class RuleSelector
    extends 
        AbstractFieldSelector<Rule>
{
    @Override
    protected void chooseItem( final Callback callback )
    {
        RulePicker.pick( this, callback );
    }
}
