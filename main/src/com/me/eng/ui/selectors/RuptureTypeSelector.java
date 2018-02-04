package com.me.eng.ui.selectors;

import com.me.eng.domain.Sample;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Matheus
 */
public class RuptureTypeSelector
    extends 
        AbstractComboboxSelector<Character>
{
    public RuptureTypeSelector()
    {
        setReadonly( true );
    }
    
    @Override
    public List<Character> getElements()
    {
        return Arrays.asList( Sample.RUPTURE_TYPES );
    }
}
