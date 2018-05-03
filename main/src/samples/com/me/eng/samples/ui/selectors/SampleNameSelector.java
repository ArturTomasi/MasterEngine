/* 
 *  Filename:    SampleNameSelector 
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
package com.me.eng.samples.ui.selectors;

import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.services.ApplicationServices;
import com.me.eng.core.ui.selectors.AbstractComboboxSelector;
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
