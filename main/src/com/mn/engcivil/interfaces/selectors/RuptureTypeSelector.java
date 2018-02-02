package com.mn.engcivil.interfaces.selectors;

import com.mn.engcivil.domain.Sample;
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
