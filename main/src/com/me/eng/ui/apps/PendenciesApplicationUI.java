package com.me.eng.ui.apps;

import com.me.eng.annotations.ApplicationDescriptor;
import com.me.eng.ui.views.PendenciesApplicationViewUI;

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
