package com.mn.engcivil.application;

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
    
    public void init()
    {
        FormulaApplicationServices.setCurrent( formulaApplicationService );
        ApplicationServices.setCurrent( applicationService );
    }
}
