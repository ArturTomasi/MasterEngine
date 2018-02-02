package com.mn.engcivil.interfaces.selectors;

import com.mn.engcivil.domain.Client;
import com.mn.engcivil.domain.Contact;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Matheus
 */
public class ContactComboboxSelector
    extends 
        AbstractComboboxSelector<Contact>
{
    private Client client;
    
    public void setClient( Client client )
    {
        this.client = client;
        
        refreshContent();
    }

    @Override
    public List<Contact> getElements()
    {
        if ( client != null )
        {
            return client.getContacs();
        }
        
        return Collections.emptyList();
    }
}
