package com.mn.engcivil.interfaces.apps;

import com.mn.engcivil.annotations.ApplicationDescriptor;
import com.mn.engcivil.interfaces.views.ConfigurationApplicationViewUI;
import com.mn.engcivil.interfaces.views.UserApplicationViewUI;

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
