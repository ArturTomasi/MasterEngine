package com.me.eng.application;

import org.zkoss.zk.ui.Executions;

/**
 *
 * @author Matheus
 */
public class FormulaApplicationServices
{
    /**
     * 
     * @return 
     */
    public static FormulaApplicationService getCurrent()
    {
        return (FormulaApplicationService) Executions.getCurrent().getSession().getAttribute( SessionVariables.FORMULA_APPLICATION_SERVICE );
    }
    
    /**
     * 
     * @param service 
     */
    static void setCurrent( FormulaApplicationService service )
    {
        Executions.getCurrent().getSession().setAttribute( SessionVariables.FORMULA_APPLICATION_SERVICE, service );
    }
}
