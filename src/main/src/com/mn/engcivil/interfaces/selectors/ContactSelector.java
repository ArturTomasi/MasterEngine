package com.mn.engcivil.interfaces.selectors;

import com.mn.engcivil.domain.Client;
import com.mn.engcivil.domain.Contact;
import com.mn.engcivil.interfaces.Callback;
import com.mn.engcivil.interfaces.pickers.ContactPicker;

/**
 *
 * @author Matheus
 */
public class ContactSelector
    extends 
        AbstractFieldSelector<Contact>
{
    private Client client;

    public void setClient( Client client )
    {
        this.client = client;
    }
    
    @Override
    protected void chooseItem( final Callback callback )
    {
        ContactPicker.pick( this, client, callback );
    }
}
