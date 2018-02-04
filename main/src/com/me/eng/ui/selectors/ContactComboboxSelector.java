package com.me.eng.ui.selectors;

import com.me.eng.domain.Client;
import com.me.eng.domain.Contact;
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
