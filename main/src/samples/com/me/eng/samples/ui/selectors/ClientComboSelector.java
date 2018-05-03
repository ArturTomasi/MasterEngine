/* 
 *  Filename:    ClientComboSelector 
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
package com.me.eng.samples.ui.selectors;

import com.me.eng.core.application.ApplicationContext;
import com.me.eng.core.services.ApplicationServices;
import com.me.eng.core.ui.selectors.AbstractComboboxSelector;
import com.me.eng.core.domain.Client;
import com.me.eng.samples.domain.SampleFilter;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

/**
 *
 * @author Matheus
 */
public class ClientComboSelector
    extends 
        AbstractComboboxSelector<Client>
{
    public ClientComboSelector()
    {
        setReadonly( true );
        setItemRenderer( new ComboitemRenderer<Client>()
        {
            @Override
            public void render( Comboitem cmbtm, Client t, int i ) throws Exception
            {
                cmbtm.setValue( t );
                cmbtm.setLabel( t.getName() );
            }
        } );
    }
    
    @Override
    public List<Client> getElements()
    {
        try
        {
            List<Client> clients = ApplicationServices.getCurrent()
                        .getClientRepository()
                        .findAll();
            
            clients.add( 0, SampleFilter.CLIENT_ALL );
            
            return clients;
        }
        
        catch ( Exception ex )
        {
            ApplicationContext.getInstance().handleException( ex );
        }
        
        return Collections.emptyList();
    }
}
