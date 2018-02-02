package com.mn.engcivil.interfaces.apps;

import com.mn.engcivil.annotations.ApplicationDescriptor;
import com.mn.engcivil.interfaces.views.PendenciesApplicationViewUI;

/**
 *
 * @author Matheus
 */
@ApplicationDescriptor( url = "/admin/pendencies.jsp",
                        icon = "ai_pendencies.png",
                        label = "Pendências" )
public class PendenciesApplicationUI
    extends 
        ApplicationUI
{
    public PendenciesApplicationUI()
    {
        addView( new PendenciesApplicationViewUI() );
    }
}
