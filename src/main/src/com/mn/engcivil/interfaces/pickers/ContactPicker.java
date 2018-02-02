package com.mn.engcivil.interfaces.pickers;

import com.mn.engcivil.domain.Client;
import com.mn.engcivil.domain.Contact;
import com.mn.engcivil.interfaces.Callback;
import com.mn.engcivil.interfaces.tables.ContactTable;
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
