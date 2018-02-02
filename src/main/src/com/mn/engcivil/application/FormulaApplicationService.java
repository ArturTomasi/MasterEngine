package com.mn.engcivil.application;

import com.mn.engcivil.domain.Formula;
import com.mn.engcivil.domain.Newtown;
import com.mn.engcivil.domain.RC;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Matheus
 */
@Named
public class FormulaApplicationService
    implements 
        Serializable
{
    @Newtown
    @Inject
    private Formula newton;
    
    @RC
    @Inject
    private Formula rc;
    
    public Formula getNewton()
    {
        return newton;
    };

    public Formula getRc()
    {
        return rc;
    }
}
