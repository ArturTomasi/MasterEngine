/* 
 *  Filename:    FormulaApplicationServices 
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
package com.me.eng.core.services;

import com.me.eng.core.application.SessionVariables;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author Matheus
 */
public class FormulaApplicationServices
{
   
    /**
     * getCurrent
     * 
     * @return FormulaApplicationService
     */
    public static FormulaApplicationService getCurrent()
    {
        return (FormulaApplicationService) Executions.getCurrent().getSession().getAttribute( SessionVariables.FORMULA_APPLICATION_SERVICE );
    }
    

    /**
     * setCurrent
     * 
     * @param service FormulaApplicationService
     */
    public static void setCurrent( FormulaApplicationService service )
    {
        Executions.getCurrent().getSession().setAttribute( SessionVariables.FORMULA_APPLICATION_SERVICE, service );
    }
}
