package com.mn.engcivil.application;

import org.zkoss.zk.ui.Executions;

/**
 *
 * @author Matheus
 */
public class FormulaApplicationServices
{
    public static FormulaApplicationService getCurrent()
    {
        return (FormulaApplicationService) Executions.getCurrent().getSession().getAttribute( 
                "com.mn.engcivil.application.FormulaApplicationService" );
    }
    
    static void setCurrent( FormulaApplicationService service )
    {
        Executions.getCurrent().getSession().setAttribute( "com.mn.engcivil.application.FormulaApplicationService", service );
    }
}
