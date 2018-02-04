package com.me.eng.ui.selectors;

import com.me.eng.domain.Rule;
import com.me.eng.ui.Callback;
import com.me.eng.ui.pickers.RulePicker;

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
