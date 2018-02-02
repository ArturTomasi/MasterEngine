package com.mn.engcivil.interfaces.apps;

import com.mn.engcivil.interfaces.views.ReportApplicationViewUI;
import com.mn.engcivil.interfaces.views.ClientReportApplicationViewUI;
import com.mn.engcivil.interfaces.views.SampleApplicationViewUI;
import com.mn.engcivil.annotations.ApplicationDescriptor;
import com.mn.engcivil.application.ApplicationContext;

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
