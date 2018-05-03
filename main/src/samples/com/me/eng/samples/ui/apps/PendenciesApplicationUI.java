/* 
 *  Filename:    PendenciesApplicationUI 
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

import com.me.eng.core.annotations.ApplicationDescriptor;
import com.me.eng.core.ui.apps.ApplicationUI;
import com.me.eng.samples.ui.views.PendenciesApplicationViewUI;

/**
 *
 * @author Matheus
 */
@ApplicationDescriptor( url = "/admin/pendencies.jsp",
                        module= "eng_samples",
                        icon = "samples/ai_pendencies.png",
                        label = "Pendências" )
public class PendenciesApplicationUI
    extends 
        ApplicationUI
{
    /**
     * PendenciesApplicationUI
     * 
     */
    public PendenciesApplicationUI()
    {
        addView( new PendenciesApplicationViewUI() );
    }
}
