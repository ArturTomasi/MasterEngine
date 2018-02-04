package com.me.eng.ui.apps;

import com.me.eng.annotations.ApplicationDescriptor;
import com.me.eng.ui.views.ConfigurationApplicationViewUI;
import com.me.eng.ui.views.UserApplicationViewUI;

/**
 *
 * @author Matheus
 */
@ApplicationDescriptor( url = "/admin/setup.jsp",
                        icon = "ai_tools.png",
                        label = "Sistema" )

public class SetupApplicationUI
    extends 
        ApplicationUI
{
    public SetupApplicationUI()
    {
        addView( new UserApplicationViewUI() );
        addView( new ConfigurationApplicationViewUI() );
    }
}
