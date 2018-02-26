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
