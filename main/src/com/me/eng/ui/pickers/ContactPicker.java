package com.me.eng.ui.pickers;

import com.me.eng.domain.Client;
import com.me.eng.domain.Contact;
import com.me.eng.ui.Callback;
import com.me.eng.ui.tables.ContactTable;
import org.zkoss.zk.ui.Component;

/**
 *
 * @author Matheus
 */
public class ContactPicker
    extends 
        PickerPanel<Contact>
{
    public static void pick( Component owner, Client client, Callback<Contact> callback )
    {
        ContactPicker contactPicker = new ContactPicker();
        contactPicker.setClient( client );
        
        DefaultPicker.createPicker( owner, contactPicker, callback );
    }

    public ContactPicker()
    {
        initComponents();
    }
    
    private void setClient( Client client )
    {
        contactTable.setElements( client.getContacs() );
    }

    @Override
    public void setSelectedItem( Contact source )
    {
        contactTable.setSelectedElement( source );
    }
    
    @Override
    public Contact getSelectedItem()
    {
        return contactTable.getSelectedElement();
    }
    
    private void initComponents()
    {
        appendChild( contactTable );
    }
    
    private ContactTable contactTable = new ContactTable();
}
