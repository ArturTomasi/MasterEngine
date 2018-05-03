/* 
 *  Filename:    ApplicationInject 
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
package com.me.eng.core.application;

import com.me.eng.core.services.FormulaApplicationService;
import com.me.eng.core.services.FormulaApplicationServices;
import com.me.eng.core.services.ApplicationService;
import com.me.eng.core.services.ApplicationServices;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Matheus
 */
@Named
@SessionScoped
public class ApplicationInject
    implements 
        Serializable
{
    @Inject 
    private ApplicationService applicationService;
    
    @Inject
    private FormulaApplicationService formulaApplicationService;

    /**
     * init
     * 
     */
    public void init()
    {
        FormulaApplicationServices.setCurrent( formulaApplicationService );
    
        ApplicationServices.setCurrent( applicationService );
    }
}
