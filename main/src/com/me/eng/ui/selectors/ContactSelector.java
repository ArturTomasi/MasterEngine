package com.me.eng.ui.selectors;

import com.me.eng.domain.Client;
import com.me.eng.domain.Contact;
import com.me.eng.ui.Callback;
import com.me.eng.ui.pickers.ContactPicker;

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
