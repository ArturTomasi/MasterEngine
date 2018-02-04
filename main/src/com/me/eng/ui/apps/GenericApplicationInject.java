package com.me.eng.ui.apps;

import com.me.eng.application.ApplicationInject;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

/**
 *
 * @author Matheus
 */
@VariableResolver(org.zkoss.zkplus.cdi.DelegatingVariableResolver.class)
public class GenericApplicationInject
    extends 
        SelectorComposer<StandaloneApplication>
{
    @WireVariable 
    ApplicationInject applicationInject;
    
    @Override
    public void doAfterCompose( StandaloneApplication comp ) throws Exception
    {
        applicationInject.init();
        
        comp.refreshContent();
    }
}
