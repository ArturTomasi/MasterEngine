/* 
 *  Filename:    GenericApplicationInject 
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
    
    /**
     * doAfterCompose
     * 
     * @param comp StandaloneApplication
     * @throws Exception
     */
    @Override
    public void doAfterCompose( StandaloneApplication comp ) throws Exception
    {
        applicationInject.init();
        
        comp.refreshContent();
    }
}
