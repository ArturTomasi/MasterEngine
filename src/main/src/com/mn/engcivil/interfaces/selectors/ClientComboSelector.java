package com.mn.engcivil.interfaces.selectors;

import com.mn.engcivil.application.ApplicationContext;
import com.mn.engcivil.application.ApplicationServices;
import com.mn.engcivil.domain.Client;
import com.mn.engcivil.domain.SampleFilter;
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
