package com.me.eng.ui.selectors;

import com.me.eng.application.ApplicationContext;
import com.me.eng.application.ApplicationServices;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Matheus
 */
public class SampleNameSelector
    extends 
        AbstractComboboxSelector<String>
{
    public SampleNameSelector()
    {
        setAutocomplete( true );
        setButtonVisible( true );
        setHflex( "true" );
    }
    
    @Override
    public List<String> getElements()
    {
        try
        {
            return ApplicationServices.getCurrent()
                            .getSampleRepository()
                            .getDistinctNames();
        }
        
        catch ( Exception ex )
        {
            ApplicationContext.getInstance().handleException( ex );
        }
        
        return Collections.emptyList();
    }
}
