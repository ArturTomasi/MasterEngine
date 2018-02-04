package com.me.eng.ui.apps;

import com.me.eng.ui.views.ReportApplicationViewUI;
import com.me.eng.ui.views.ClientReportApplicationViewUI;
import com.me.eng.ui.views.SampleApplicationViewUI;
import com.me.eng.annotations.ApplicationDescriptor;
import com.me.eng.application.ApplicationContext;

/**
 *
 * @author Matheus
 */
@ApplicationDescriptor( url = "/admin/administrator.jsp",
                        icon = "ai_administrator.png",
                        label = "Adminstração de Amostras" )

public class AdministratorApplicationUI
    extends 
        ApplicationUI
{
    public AdministratorApplicationUI()
    {
        addView( new SampleApplicationViewUI() );
        
        if ( ApplicationContext.getInstance().getActiveUser().isAdministrator() )
        {
            addView( new ReportApplicationViewUI() );
        }
        
        addView( new ClientReportApplicationViewUI() );
    }
}
