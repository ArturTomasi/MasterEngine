package com.me.eng.application;

import com.me.eng.domain.Formula;
import com.me.eng.domain.Newtown;
import com.me.eng.domain.RC;
import java.io.Serializable;
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
