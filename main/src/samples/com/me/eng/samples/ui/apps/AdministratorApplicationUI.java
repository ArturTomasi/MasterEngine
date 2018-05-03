/* 
 *  Filename:    AdministratorApplicationUI 
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
package com.me.eng.samples.ui.apps;

import com.me.eng.samples.ui.views.ReportApplicationViewUI;
import com.me.eng.samples.ui.views.ClientReportApplicationViewUI;
import com.me.eng.samples.ui.views.SampleApplicationViewUI;
import com.me.eng.core.annotations.ApplicationDescriptor;
import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.ui.apps.ApplicationUI;

/**
 *
 * @author Matheus
 */
@ApplicationDescriptor( url = "/admin/administrator.jsp",
                        icon = "samples/ai_administrator.png",
                        module= "eng_samples",
                        label = "Adminstração de Amostras" )

public class AdministratorApplicationUI
    extends 
        ApplicationUI
{
    /**
     * AdministratorApplicationUI
     * 
     */
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
