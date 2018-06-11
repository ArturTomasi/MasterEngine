/*
 *  Filename:    FinanceApplicationView
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
 *  is the property of Over Line Ltda.
 *  The program(s) may be used and/or copied only with
 *  the written permission of Over Line Ltda,
 *  or in accordance with the terms and conditions
 *  stipulated in the agreement/contract under which
 *  the program(s) have been supplied.
 */
package com.me.eng.finances.ui.apps;

import com.me.eng.core.annotations.ApplicationDescriptor;
import com.me.eng.core.ui.apps.ApplicationUI;
import com.me.eng.finances.ui.views.CompanyApplicationViewUI;
import com.me.eng.finances.ui.views.PostingCategoryApplicationViewUI;
import com.me.eng.finances.ui.views.FinanceApplicationViewUI;

/**
 *
 * @author Artur Tomasi
 */
@ApplicationDescriptor( url = "/admin/finance.jsp",
                        icon = "finances/ai_finance.png",
                        module= "eng_finances",
                        label = "Adminstração de Finanças" )
public class FinanceApplicationUI 
    extends 
        ApplicationUI
{
    
    /**
     * FinanceApplicationUI
     * 
     */
    public FinanceApplicationUI() 
    {
        addView( new FinanceApplicationViewUI() );
        addView( new PostingCategoryApplicationViewUI() );
        addView( new CompanyApplicationViewUI() );
    }
}
